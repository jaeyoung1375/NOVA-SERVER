package kr.co.nova.configuration;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.util.Locale;

import org.slf4j.MDC;

public class P6spyPrettySqlFormatter implements MessageFormattingStrategy {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String indent = "        ";

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
            String prepared, String sql, String url) {

		if (sql == null || sql.trim().isEmpty() || !"statement".equalsIgnoreCase(category)) {
		return ""; // null 반환 금지
		}

		 // 키워드 기준 줄바꿈 + 대문자
	    String formattedSql = alignColumns(sql.trim());
	    String mapperId = MDC.get("P6SPY_CATEGORY");
	    if (mapperId == null) {
	        mapperId = category; // 기본 fallback
	    }
	    // 패키지 제거: kr.co.nova.api.concert.mapper.ConcertMapper.getConcertList → ConcertMapper.getConcertList
	    mapperId = mapperId.substring(mapperId.lastIndexOf('.', mapperId.lastIndexOf('.') - 1) + 1);


	    return NEW_LINE + NEW_LINE +"/* [" + mapperId + "] */\n" + formattedSql;
		}

    private String alignColumns(String sql) {
        sql = sql.replace("\t", "    "); // 탭 → 공백
        String[] lines = sql.lines().filter(line -> !line.isBlank()).toArray(String[]::new); // CDATA·동적태그가 남긴 빈 줄 제거
        int maxIdx = 0;

        for (String line : lines) {
            int idx = line.indexOf("/*");
            if (idx > maxIdx) maxIdx = idx;
        }

        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            int idx = line.indexOf("/*");
            if (idx >= 0) {
                int pad = maxIdx - idx;
                sb.append(line, 0, idx)
                  .append(" ".repeat(pad))
                  .append(line.substring(idx))
                  .append("\n");
            } else {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
}