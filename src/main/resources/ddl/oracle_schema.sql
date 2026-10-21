-- =============================================================================
-- NOVA-SERVER: Oracle 기본 스키마 DDL
-- flick-server에서 가져온 인프라(로그인 · 파일 · 공통코드 · 토스 결제) 테이블
-- =============================================================================
--
-- [작성 근거]
-- flick-server postgres_schema.sql(원본 Oracle MOTIVE 스키마 실측 기반)을 Oracle로 되돌림.
-- PAYMENT_HISTORY는 flick subscription_payment.sql 기준에서 SUBSCRIPTION_ID를 제거하고,
-- 매퍼가 사용하는 PAYMENT_KEY를 추가함.
--
-- [주의]
-- 예전 MOTIVE 스키마에는 USERS / SOCIAL_ACCOUNTS / CMM_* 가 이미 존재한다.
-- 같은 스키마에 붙는다면 PAYMENT_HISTORY(+시퀀스)만 실행할 것.
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 시퀀스
-- -----------------------------------------------------------------------------
CREATE SEQUENCE CMM_FILE_SEQ        START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE USERS_SEQ           START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SOCIAL_SEQ          START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE PAYMENT_HISTORY_SEQ START WITH 1 INCREMENT BY 1;

-- -----------------------------------------------------------------------------
-- CMM_FILE (공통 파일)
-- -----------------------------------------------------------------------------
CREATE TABLE CMM_FILE (
    FILE_ID       NUMBER         NOT NULL,
    ORG_FILE_NM   VARCHAR2(255)  NOT NULL,
    SAVE_FILE_NM  VARCHAR2(255)  NOT NULL,
    FILE_PATH     VARCHAR2(100)  NOT NULL,
    FILE_SIZE     NUMBER         NULL,
    FILE_EXT      VARCHAR2(20)   NOT NULL,
    DEL_YN        CHAR(1)        NULL,
    DEL_DT        DATE           NULL,
    TEMP_YN       CHAR(1)        NULL,
    TEMP_KEY      VARCHAR2(200)  NULL,
    CONSTRAINT CMM_FILE_PK PRIMARY KEY (FILE_ID)
);

COMMENT ON TABLE CMM_FILE IS '공통 파일';

-- -----------------------------------------------------------------------------
-- CMM_CODE (공통코드)
-- -----------------------------------------------------------------------------
CREATE TABLE CMM_CODE (
    COM_CD_ID       VARCHAR2(100)  NOT NULL,
    COM_CD_NM       VARCHAR2(100)  NOT NULL,
    COM_CD_EXPLN    VARCHAR2(100)  NULL,
    USE_YN          CHAR(1)        DEFAULT 'Y' NULL,
    LINK_COM_CD_ID1 VARCHAR2(100)  NULL,
    LINK_COM_CD_ID2 VARCHAR2(100)  NULL,
    REG_ID          VARCHAR2(50)   NOT NULL,
    REG_DT          DATE           DEFAULT SYSDATE NOT NULL,
    MOD_ID          VARCHAR2(50)   NULL,
    MOD_DT          DATE           NULL,
    SORT_SEQ        NUMBER         NULL,
    CONSTRAINT PK_CMM_CODE PRIMARY KEY (COM_CD_ID)
);

COMMENT ON TABLE CMM_CODE IS '공통코드';

-- -----------------------------------------------------------------------------
-- CMM_CODE_DTL (상세코드)
-- -----------------------------------------------------------------------------
CREATE TABLE CMM_CODE_DTL (
    COM_CD_ID       VARCHAR2(100)  NOT NULL,
    DTL_CD_ID       VARCHAR2(100)  NOT NULL,
    DTL_CD_NM       VARCHAR2(200)  NOT NULL,
    DTL_CD_EXPLN    VARCHAR2(1000) NULL,
    USE_YN          VARCHAR2(1)    DEFAULT 'Y' NULL,
    SORT_SEQ        NUMBER         NULL,
    LNKG_DTL_CD_ID1 VARCHAR2(100)  NULL,
    LNKG_DTL_CD_NM1 VARCHAR2(100)  NULL,
    LNKG_DTL_CD_ID2 VARCHAR2(100)  NULL,
    LNKG_DTL_CD_NM2 VARCHAR2(100)  NULL,
    REG_ID          VARCHAR2(50)   NOT NULL,
    REG_DT          DATE           DEFAULT SYSDATE NOT NULL,
    MOD_ID          VARCHAR2(50)   NULL,
    MOD_DT          DATE           NULL,
    CONSTRAINT PK_CMM_CODE_DTL PRIMARY KEY (COM_CD_ID, DTL_CD_ID),
    CONSTRAINT FK_CMM_CODE_DTL_01 FOREIGN KEY (COM_CD_ID) REFERENCES CMM_CODE (COM_CD_ID)
);

COMMENT ON TABLE CMM_CODE_DTL IS '상세코드';

-- -----------------------------------------------------------------------------
-- USERS (회원)
-- -----------------------------------------------------------------------------
CREATE TABLE USERS (
    USER_ID          NUMBER         NOT NULL,
    EMAIL            VARCHAR2(255)  NULL,
    PASSWORD_HASH    VARCHAR2(255)  NULL,
    STATUS           VARCHAR2(20)   NOT NULL,
    NAME             VARCHAR2(50)   NOT NULL,
    PHONE            VARCHAR2(20)   NULL,
    "ROLE"           VARCHAR2(20)   NOT NULL,
    REG_DT           DATE           NULL,
    MOD_DT           DATE           NULL,
    PROFILE_FILE_ID  NUMBER         NULL,
    LAST_LOGIN_DT    DATE           NULL,
    GENDER           CHAR(2)        NULL,
    BIRTH            DATE           NULL,
    NICKNAME         VARCHAR2(50)   NULL,
    CONSTRAINT USERS_PK PRIMARY KEY (USER_ID),
    CONSTRAINT USERS_CMM_FILE_FK FOREIGN KEY (PROFILE_FILE_ID) REFERENCES CMM_FILE (FILE_ID)
);

COMMENT ON TABLE USERS IS '회원';

-- -----------------------------------------------------------------------------
-- SOCIAL_ACCOUNTS (소셜 로그인 연동)
-- -----------------------------------------------------------------------------
CREATE TABLE SOCIAL_ACCOUNTS (
    SOCIAL_ID              NUMBER         NOT NULL,
    USER_ID                NUMBER         NULL,
    PROVIDER               VARCHAR2(100)  NOT NULL,
    PROVIDER_USER_ID       VARCHAR2(100)  NOT NULL,
    REG_DT                 DATE           NULL,
    MOD_DT                 DATE           NULL,
    PROFILE_FILE_ID        NUMBER         NULL,
    PROVIDER_ACCESS_TOKEN  CLOB           NULL,
    EXPIRES_AT             DATE           NULL,
    CONSTRAINT SOCIAL_ACCOUNTS_PK PRIMARY KEY (SOCIAL_ID),
    CONSTRAINT SOCIAL_ACCOUNTS_USERS_FK FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID),
    CONSTRAINT SOCIAL_ACCOUNTS_CMM_FILE_FK FOREIGN KEY (PROFILE_FILE_ID) REFERENCES CMM_FILE (FILE_ID),
    CONSTRAINT SOCIAL_ACCOUNTS_UNIQUE_NEW UNIQUE (PROVIDER, PROVIDER_USER_ID)
);

COMMENT ON TABLE SOCIAL_ACCOUNTS IS '소셜 로그인 연동';

-- -----------------------------------------------------------------------------
-- PAYMENT_HISTORY (결제 이력 - 토스페이먼츠 승인 결과)
-- -----------------------------------------------------------------------------
CREATE TABLE PAYMENT_HISTORY (
    PAYMENT_ID        NUMBER         NOT NULL,
    USER_ID           NUMBER         NOT NULL,
    PG_TRANSACTION_ID VARCHAR2(64)   NOT NULL,               -- 토스 orderId
    PAYMENT_KEY       VARCHAR2(200)  NULL,                   -- 토스 paymentKey
    AMOUNT            NUMBER(12,0)   NOT NULL,
    STATUS            VARCHAR2(20)   NOT NULL,               -- READY, DONE, FAILED, CANCELED
    APPROVED_AT       TIMESTAMP      NULL,
    RAW_RESPONSE      CLOB           NULL,                   -- 토스 API 응답 원문(JSON)
    REG_ID            VARCHAR2(50)   NOT NULL,
    REG_DT            DATE           DEFAULT SYSDATE NOT NULL,
    MOD_ID            VARCHAR2(50)   NULL,
    MOD_DT            DATE           NULL,
    CONSTRAINT PK_PAYMENT_HISTORY PRIMARY KEY (PAYMENT_ID),
    CONSTRAINT FK_PAYMENT_HISTORY_USERS FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID),
    CONSTRAINT UQ_PAYMENT_HISTORY_PG_TRANSACTION_ID UNIQUE (PG_TRANSACTION_ID),
    CONSTRAINT CK_PAYMENT_HISTORY_RAW_JSON CHECK (RAW_RESPONSE IS JSON)
);

COMMENT ON TABLE PAYMENT_HISTORY IS '결제 이력 (토스페이먼츠 승인 결과)';
