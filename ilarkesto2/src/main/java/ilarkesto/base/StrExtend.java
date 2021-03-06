/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.base;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import static ilarkesto.base.UtlExtend.randomChar;
import static ilarkesto.base.UtlExtend.randomInt;
import ilarkesto.integration.links.LinkConverter;
import ilarkesto.logging.Log;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringReader;
import static java.lang.System.arraycopy;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.emptyList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import static java.lang.Character.toUpperCase;
import static java.lang.Integer.valueOf;
import static java.lang.Math.min;
import static java.lang.String.valueOf;

/**
 * Utility methods for dealing with text/strings. Cutting, comparing, parsing,
 * modifying.
 */
public class StrExtend extends ilarkesto.core.base.Str {
    
    private static final Log LOG = Log.get(StrExtend.class);

    private static final char[] UNICODE_CHARS = new char[]{UESMALL, UE, OESMALL, OE, AESMALL, AE, SZ, EUR};

    private static final String[][] ESCAPE_SEQUENCES = {{"\\", "\\\\"}, {"\b", "\\b"}, {"\t", "\\t"},
    {"\n", "\\n"}, {"\f", "\\f"}, {"\r", "\\r"}, {"\"", "\\\""}, {"\'", "\\\'"}};

    public static String getCharsetFromHtml(String html, String defaultCharset) {
        String charset = cutFromTo(html, "charset=", "\"");
        if (isBlank(charset)) {
            charset = defaultCharset;
        }
        return charset;
    }

    public static String[] tokenize(String s, String delimiter) {
        StringTokenizer tok = new StringTokenizer(s, delimiter);
        LinkedList<String> ll = new LinkedList<>();
        while (tok.hasMoreTokens()) {
            ll.add(tok.nextToken());
        }
        return toStringArray(ll);
    }

    public static String multiply(String s, int factor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < factor; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static String generateRandomParagraphs(int count) {
        return generateRandomParagraphs(count, null, null, "\n\n");
    }

    public static String generateRandomParagraphs(int count, String prefix, String suffix, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            if (separator != null && i > 0) {
                sb.append(separator);
            }
            if (prefix != null) {
                sb.append(prefix);
            }
            sb.append(generateRandomParagraph());
            if (suffix != null) {
                sb.append(suffix);
            }
        }
        return sb.toString();
    }

    public static String generateRandomParagraph() {
        return generateRandomParagraph(2, 10, 4, 12, 2, 12);
    }

    public static String generateRandomParagraph(int minSentences, int maxSentences, int minWords, int maxWords,
            int minWordLenght, int maxWordLenght) {
        int sentences = randomInt(minSentences, maxSentences);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentences; i++) {
            String sentence = generateRandomSentence(minWords, maxWords, minWordLenght, maxWordLenght);
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(sentence).append(".");
        }
        return sb.toString();
    }

    public static String generateRandomSentence() {
        return generateRandomSentence(4, 12);
    }

    public static String generateRandomSentence(int minWords, int maxWords) {
        return generateRandomSentence(minWords, maxWords, 2, 12);
    }

    public static String generateRandomSentence(int minWords, int maxWords, int minWordLenght, int maxWordLenght) {
        int words = randomInt(minWords, maxWords);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words; i++) {
            boolean uppercase = i == 0 || randomInt(0, 9) == 0;
            String word = generateRandomWord(minWordLenght, maxWordLenght, uppercase);
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(word);
        }
        return sb.toString();
    }

    public static String generateRandomWord(int minLength, int maxLength, boolean uppercase) {
        String vovels = "aeiouy";
        String consonants = "bcdfghjklmnpqrstvwxz";
        int length = randomInt(minLength, maxLength);
        String word = generateRandomWord(vovels, consonants, length);
        return uppercase ? uppercaseFirstLetter(word) : word;
    }

    public static String generatePassword(int length) {
        return generateRandomWord("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!$%&=", length);
    }

    public static String generateRandomWord(String availableChars, int minLength, int maxLength) {
        int length = randomInt(minLength, maxLength);
        return generateRandomWord(availableChars, length);
    }

    public static String generateRandomWord(String charSet1, String charSet2, int length) {
        StringBuilder password = new StringBuilder();
        String charSet = charSet1;
        for (int i = 0; i < length; i++) {
            if (randomInt(0, 8) != 0) {
                if (charSet == null ? charSet1 == null : charSet.equals(charSet1)) {
                    charSet = charSet2;
                } else {
                    charSet = charSet1;
                }
            }
            password.append(randomChar(charSet));
        }
        return password.toString();
    }

    public static String generateRandomWord(String availableChars, int length) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(randomChar(availableChars));
        }
        return password.toString();
    }

    public static boolean containsNonLetterOrDigit(String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean endsWith(String s, String... suffixes) {
        for (String suffix : suffixes) {
            if (s.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    public static String formatWithThousandsSeparator(long value, String separator) {
        return formatWithThousandsSeparator(valueOf(value), separator);
    }

    public static String formatWithThousandsSeparator(String s, String separator) {
        if (s == null) {
            return null;
        }
        if (separator == null || s.length() <= 3) {
            return s;
        }
        boolean negative = false;
        if (s.startsWith("-")) {
            negative = true;
            s = s.substring(1);
        }
        if (s.length() > 3) {
            s = s.substring(0, s.length() - 3) + separator + s.substring(s.length() - 3);
        }
        if (s.length() > 7) {
            s = s.substring(0, s.length() - 7) + separator + s.substring(s.length() - 7);
        }
        if (s.length() > 11) {
            s = s.substring(0, s.length() - 11) + separator + s.substring(s.length() - 11);
        }
        if (negative) {
            s = '-' + s;
        }
        return s;
    }

    public static String replaceUnicodeCharsWithJavaNotation(String s) {
        s = s.replace(valueOf(UESMALL), "\\u00FC");
        s = s.replace(valueOf(UE), "\\u00DC");
        s = s.replace(valueOf(OESMALL), "\\u00F6");
        s = s.replace(valueOf(OE), "\\u00D6");
        s = s.replace(valueOf(AESMALL), "\\u00E4");
        s = s.replace(valueOf(AE), "\\u00C4");
        s = s.replace(valueOf(SZ), "\\u00DF");
        s = s.replace(valueOf(EUR), "\\u0080");
        return s;
    }

    public static boolean containsUnicodeChar(String s) {
        return containsChar(s, UNICODE_CHARS);
    }

    public static boolean containsChar(String s, char... chars) {
        for (char c : chars) {
            if (s.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static String activateLinksInHtml(String s, LinkConverter linkConverter) {
        return activateLinksInHtml(s, linkConverter, 640);
    }

    public static String activateLinksInHtml(String s, LinkConverter linkConverter, int maxWidth) {
        if (s == null) {
            return null;
        }
        int fromIndex = 0;
        StringBuffer result = null;
        int idx;
        while ((idx = firstIndexOf(s, fromIndex, "http://", "https://", "ftp://", "www.")) >= 0) {
            char pre = idx == 0 ? ' ' : s.charAt(idx - 1);
            if (pre == ' ' || pre == '\n' || pre == '>') {
                int endIdx = firstIndexOf(s, idx, " ", "<", "\n");
                if (endIdx <= 0 || !s.substring(endIdx).startsWith("</a>")) {
                    if (endIdx < 0) {
                        endIdx = s.length();
                    }
                    // activate
                    String url = s.substring(idx, endIdx);
                    if (result == null) {
                        result = new StringBuffer();
                    }
                    result.append(s.substring(fromIndex, idx));
                    result.append("<a href=\"");
                    result.append(url.startsWith("www.") ? "http://" + url : url);
                    result.append("\" target=\"_blank\">");

                    String convertedUrl = linkConverter.convert(url, maxWidth);
                    if (convertedUrl.equals(url)) {
                        if (url.startsWith("http://")) {
                            url = url.substring(7);
                        }
                        if (url.startsWith("https://")) {
                            url = url.substring(8);
                        }
                        if (url.startsWith("www.")) {
                            url = url.substring(4);
                        }
                        String label = cutRight(url, 30, "...");
                        result.append(label);
                    } else {
                        result.append(convertedUrl);
                    }

                    result.append("</a>");
                    fromIndex = endIdx;
                    continue;
                }
            }
            // nothing to activate
            if (result == null) {
                result = new StringBuffer();
            }
            result.append(s.substring(fromIndex, idx));
            result.append(s.charAt(idx));
            fromIndex = idx + 1;
        }
        if (result == null) {
            return s;
        }
        result.append(s.substring(fromIndex));
        return result.toString();
    }

    public static int firstIndexOf(String s, int fromIndex, String... stringsToFind) {
        int idx = Integer.MAX_VALUE;
        for (String find : stringsToFind) {
            int i = s.indexOf(find, fromIndex);
            if (i >= 0) {
                idx = min(idx, i);
            }
        }
        idx = idx == Integer.MAX_VALUE ? -1 : idx;
        return idx;
    }

    public static boolean isUpperCase(String s) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isUpperCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getFirstTokenAfter(String stringToTokenize, String delimiter, String s) {
        return getTokenAfter(stringToTokenize, delimiter, s, 0);
    }

    public static String getTokenAfter(String stringToTokenize, String delimiter, String s, int index) {
        return getStringAfter(tokenize(stringToTokenize, delimiter), s, index);
    }

    public static String getFirstStringAfter(String[] strings, String s) {
        return getStringAfter(strings, s, 0);
    }

    /**
     * Determine the string, which is at a specified position after a specified
     * string.
     * @param strings
     * @param s
     * @param index
     * @return 
     */
    public static String getStringAfter(String[] strings, String s, int index) {
        int i = indexOfStringInArray(s, strings);
        if (i < 0) {
            return null;
        }
        index += i + 1;
        if (index >= s.length()) {
            return null;
        }
        return strings[index];
    }

    /**
     * Determine the index of a string inside of an string array.
     */
    private static int indexOfStringInArray(String s, String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            if (equalString(strings[i], s)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean equalString(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1 == null) {
            return false;
        }
        if (s2 == null) {
            return false;
        }
        return s1.equals(s2);
    }

    public static Collection<String> parseCommaSeparatedString(String s) {
        Collection<String> result = new ArrayList<>();
        if (s == null) {
            return result;
        }
        StringTokenizer tokenizer = new StringTokenizer(s, ",");
        while (tokenizer.hasMoreTokens()) {
            result.add(tokenizer.nextToken().trim());
        }
        return result;
    }

    public static String decodeQuotedPrintable(String s) {
        if (s == null) {
            return null;
        }

        int start = s.indexOf("=?");
        if (start < 0) {
            return s;
        }

        String prefix = s.substring(0, start);

        start = s.indexOf("?Q?", start);
        if (start < 0) {
            start = s.indexOf("?q?", start);
        }
        if (start < 0) {
            start = s.indexOf("?B?", start);
        }
        if (start < 0) {
            start = s.indexOf("?b?", start);
        }
        if (start < 0) {
            return s;
        }
        start += 3;

        int end = s.indexOf("?=", start);
        if (end < 0) {
            return s;
        }

        String suffix = s.substring(end + 2);
        s = s.substring(start, end);

        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '=') {
                if (i + 1 == len) {
                    sb.append('=');
                    break;
                }
                char next = s.charAt(i + 1);
                if (next == 13 || next == 10) {
                    i++;
                    next = s.charAt(i + 1);
                    if (next == 13 || next == 10) {
                        i++;
                    }
                    continue;
                }
                if (next == '=') {
                    sb.append('=');
                    i++;
                    continue;
                }
                char afterNext = s.charAt(i + 2);
                int ch = valueOf(valueOf(next) + afterNext, 16);
                sb.append((char) ch);
                i += 2;
            } else if (c == '_') {
                sb.append(' ');
            } else {
                sb.append(c);
            }
        }
        sb.append(suffix);
        return sb.toString();
    }

    public static boolean isFilenameCompatible(String s) {
        for (int i = 0, n = s.length(); i < n; i++) {
            char c = s.charAt(i);
            if (!(Character.isDigit(c) || Character.isLetter(c) || c == '_' || c == '-' || c == '.')) {
                return false;
            }
        }
        return true;
    }

    public static String uppercaseFirstLetter(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(toUpperCase(s.charAt(0)));
        sb.append(s.substring(1));
        return sb.toString();
    }

    public static String lowercaseFirstLetter(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toLowerCase(s.charAt(0)));
        sb.append(s.substring(1));
        return sb.toString();
    }

    public static String concatWithUppercaseFirstLetter(String[] sa, boolean ignoreFirst) {
        StringBuilder sb = new StringBuilder();
        if (ignoreFirst) {
            sb.append(sa[0]);
        }
        for (int i = ignoreFirst ? 1 : 0; i < sa.length; i++) {
            sb.append(toUpperCase(sa[i].charAt(0)));
            sb.append(sa[i].substring(1));
        }
        return sb.toString();
    }

    public static String formatUrl(String url) {
        if (url == null) {
            return null;
        }
        url = url.trim();
        if (url.startsWith("www.")) {
            url = "http://" + url;
        }
        return url;
    }

    public static String formatPostalcode(String s) {
        return formatNumber(s);
    }

    public static String formatNumber(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String formatTelephone(String telnum) {
        if (telnum == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean lastWasSpace = false;
        for (int i = 0; i < telnum.length(); i++) {
            char c = telnum.charAt(i);
            if (Character.isDigit(c) || c == '+') {
                sb.append(c);
                lastWasSpace = false;
            } else if (!lastWasSpace) {
                sb.append(' ');
                lastWasSpace = true;
            }
        }
        telnum = sb.toString().trim();
        if (telnum.startsWith("0") && telnum.length() > 1) {
            telnum = "+49 " + telnum.substring(1);
        }
        return telnum;
    }

    public static String[] tokenizeWithLongDelimiter(String s, String delimiter) {
        List<String> sl = new ArrayList<>();
        int idx;
        int offset = 0;
        while ((idx = s.indexOf(delimiter, offset)) >= 0) {
            sl.add(s.substring(offset, idx));
            offset = idx + delimiter.length();
        }
        sl.add(s.substring(offset));
        return toStringArray(sl);
    }

    public static char getFirstNonexistingChar(String s) {
        return getFirstNonexistingChar(s, (char) 0);
    }

    public static char getFirstNonexistingChar(String s, char offset) {
        for (char c = offset; c < Character.MAX_VALUE; c++) {
            if (s.indexOf(c) < 0) {
                return c;
            }
        }
        return Character.MAX_VALUE;
    }

    public static String constructUrl(String base, Map parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(base);
        if (parameters != null && parameters.size() > 0) {
            boolean first = true;
            Set<Map.Entry> entries = parameters.entrySet();
            for (Map.Entry entry : entries) {
                if (first) {
                    sb.append('?');
                    first = false;
                } else {
                    sb.append("&");
                }

                Object key = entry.getKey();
                Object value = entry.getValue();
                sb.append(key);
                sb.append("=");
                if (value != null) {
                    sb.append(encodeUrlParameter(value.toString()));
                }
            }
        }
        return sb.toString();
    }

    public static String toFileCompatibleString(String s) {
        s = s.replace('/', '-');
        s = s.replace('\\', '-');
        s = s.replace(':', '_');
        s = s.replace(';', '_');
        s = s.replace('&', '@');
        s = s.replace('?', '@');
        s = s.replace('=', '_');
        s = s.replace(valueOf(AESMALL), "ae");
        s = s.replace(valueOf(AE), "Ae");
        s = s.replace(valueOf(UESMALL), "ue");
        s = s.replace(valueOf(UE), "Ue");
        s = s.replace(valueOf(OESMALL), "oe");
        s = s.replace(valueOf(OE), "Oe");
        s = s.replace(valueOf(SZ), "ss");
        s = s.replace(valueOf(EUR), "EUR");
        return s;
    }

    public static String getPrimitiveTypeName(String className) {
        if (className.startsWith("java.lang.")) {
            className = className.substring(10);
        }
        if (className.equals("Long")) {
            className = "long";
        }
        if (className.equals("Integer")) {
            className = "int";
        }
        if (className.equals("Double")) {
            className = "double";
        }
        if (className.equals("Boolean")) {
            className = "boolean";
        }
        if (className.equals("Byte")) {
            className = "byte";
        }
        return className;
    }

    public static List<String> listRelativeFiles(String root, boolean recurse, boolean replaceToUnixSlash,
            boolean ignoreDirs, FileFilter filter) {
        List<String> sl = new ArrayList<>();
        listRelateveFiles(sl, root, "", recurse, replaceToUnixSlash, ignoreDirs, true, filter);
        return sl;
    }

    public static void listRelateveFiles(List<String> container, String root, String prefix, boolean recurse,
            boolean replaceToUnixSlash, boolean ignoreDirs, boolean allowDuplicates, FileFilter filter) {

        if (replaceToUnixSlash) {
            root = root.replace("\\", "/");
        }

        File rootFile = new File(root);
        File[] files = rootFile.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        for (File file : files) {
            if (filter != null && !filter.accept(file)) {
                continue;
            }
            boolean dir = file.isDirectory();
            if (!dir || !ignoreDirs) {
                String s = prefix + file.getName();
                if (allowDuplicates || !container.contains(s)) {
                    container.add(s);
                }
            }
            if (dir && recurse) {
                listRelateveFiles(container, root + "/" + file.getName(), prefix + file.getName() + "/", recurse, replaceToUnixSlash, ignoreDirs, allowDuplicates, filter);
            }
        }
    }

    public static boolean isLetterOrDigit(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLetter(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            if (!Character.isLetter(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMatchingKey(String s, String key) {
        if (s != null) {
            return s.toLowerCase().indexOf(key) >= 0;
        } else {
            return false;
        }
    }

    public static boolean isDigit(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getEnclosed(String s, String opener, String closer) {
        return (new EncloseParser(s, opener, closer)).getEnclosed();
    }

    public static class EncloseParser {

        private String opener;

        private String closer;

        private String s;

        private String prefix;

        private String enclosed;

        private String postfix;

        public EncloseParser(String s, String opener, String closer) {
            this.s = s;
            this.opener = opener;
            this.closer = closer;

            parse();
        }

        private void parse() {
            int idx = s.indexOf(opener);
            if (idx < 0) {
                return;
            }
            prefix = s.substring(0, idx);
            idx += opener.length();

            int stack = 1;
            StringBuilder sb = new StringBuilder();
            int startIdx = idx;
            String[] openClose = new String[]{opener, closer};
            int maxLen = min(opener.length(), closer.length());

            while (stack > 0) {
                if (startIdx == s.length() - 1) {
                    return;
                }
                idx = indexOf(s, openClose, startIdx);
                if (idx < 0) {
                    return;
                }
                String rest = s.substring(idx, idx + maxLen);
                if (rest.startsWith(opener)) {
                    stack++;
                    sb.append(s.substring(startIdx, (startIdx = idx + opener.length())));
                } else {
                    stack--;
                    sb.append(s.substring(startIdx, idx));
                    if (stack != 0) {
                        sb.append(closer);
                    }
                    startIdx = idx + closer.length();
                }
            }
            postfix = s.substring(startIdx);

            enclosed = sb.toString();

        }

        public String getEnclosed() {
            return enclosed;
        }

        public String getPostfix() {
            return postfix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

    public static String replaceForSql(String s) {
        s = s.replace("\\", "\\\\");
        s = s.replace("\"", "\\\"");
        return s;
    }

    public static String getHtmlBody(String s) {
        if (s == null) {
            return null;
        }

        // TODO convert encoding if not UTF-8
        int idx = s.indexOf("<body");
        if (idx < 0) {
            idx = s.indexOf("<BODY");
        }
        if (idx < 0) {
            return s;
        }

        int startIdx = s.indexOf('>', idx);
        if (startIdx < 0) {
            return s;
        }
        startIdx++;

        int endIdx = s.indexOf("</body>", startIdx);
        if (endIdx < 0) {
            endIdx = s.indexOf("</BODY>", startIdx);
        }
        if (endIdx < 0) {
            return s.substring(startIdx);
        }

        return s.substring(startIdx, endIdx);
    }

    private static boolean isTag(String tag, String name) {
        return tag.equals(name) || tag.startsWith(name + " ");
    }

    public static String removeHtmlTags(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        boolean inside = false;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (inside) {
                // inside html tag
                if (c == '>') {
                    inside = false;
                }
            } else {
                // outside html tag
                if (c == '<') {
                    inside = true;
                    continue;
                }
                sb.append(c);
            }
        }

        s = sb.toString();
        s = s.replace("&nbsp;", " ");
        s = s.replace("&auml;", valueOf(AESMALL));
        s = s.replace("&uuml;", valueOf(UESMALL));
        s = s.replace("&ouml;", valueOf(OESMALL));
        s = s.replace("&Auml;", valueOf(AE));
        s = s.replace("&Uuml;", valueOf(UE));
        s = s.replace("&Ouml;", valueOf(OE));
        s = s.replace("&szlig;", valueOf(SZ));
        s = s.replace("&euro;", valueOf(EUR));
        s = s.replace("&amp;", "&");
        s = s.replace("&quot;", "\"");
        s = s.replace("&lt;", "<");
        s = s.replace("&gt;", ">");
        s = s.replace("<br>", "\n");
        return s;
    }

    public static String[] remove(int index, String[] elements) {
        String[] sa = new String[elements.length - 1];
        arraycopy(elements, 0, sa, 0, index);
        arraycopy(elements, index + 1, sa, index, sa.length - index);
        return sa;
    }

    public static String[] remove(String elementToRemove, String[] elements) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals(elementToRemove)) {
                return remove(i, elements);
            }
        }
        return elements;
    }

    public static String appendIfMarkerNotExists(String s, String marker, String textToAppend) {
        if (s == null) {
            return marker + textToAppend;
        }
        if (s.indexOf(marker) >= 0) {
            return s;
        }
        return s + marker + textToAppend;
    }

    public static String[] append(String newElement, String[] elements) {
        String[] sa = new String[elements.length + 1];
        arraycopy(elements, 0, sa, 0, elements.length);
        sa[elements.length] = newElement;
        return sa;
    }

    public static boolean contains(String textToLookFor, String[] textsToLookIn) {
        for (String textsToLookIn1 : textsToLookIn) {
            if (textsToLookIn1.equals(textToLookFor)) {
                return true;
            }
        }
        return false;
    }

    public static String toHtmlColor(Color c) {
        StringBuilder sb = new StringBuilder("#");
        String s;

        s = Integer.toHexString(c.getRed());
        if (s.length() == 1) {
            sb.append('0');
        }
        sb.append(s);

        s = Integer.toHexString(c.getGreen());
        if (s.length() == 1) {
            sb.append('0');
        }
        sb.append(s);

        s = Integer.toHexString(c.getBlue());
        if (s.length() == 1) {
            sb.append('0');
        }
        sb.append(s);

        return sb.toString();
    }

    public static String fillUpLeft(String s, String filler, int minLength) {
        // TODO: optimize algorithm
        while (s.length() < minLength) {
            s = filler + s;
        }
        return s;
    }

    public static String cutLeft(String s, int maxlength, String fillerOnCut) {
        if (s == null) {
            return null;
        }
        if (s.length() > maxlength) {
            return fillerOnCut + s.substring(s.length() - maxlength + fillerOnCut.length());
        } else {
            return s;
        }
    }

    public static String cutRight(String s, int maxlength) {
        if (s == null) {
            return null;
        }
        if (s.length() > maxlength) {
            return s.substring(0, maxlength);
        } else {
            return s;
        }
    }

    public static String cutRight(String s, int maxlength, String fillerOnCut) {
        if (s == null) {
            return null;
        }
        if (s.length() > maxlength) {
            return s.substring(0, maxlength - fillerOnCut.length()) + fillerOnCut;
        } else {
            return s;
        }
    }

    public static String cutHtmlAndHeaderAndBody(String s) {
        if (s == null) {
            return null;
        }
        if (s.startsWith("<html")) {
            int idx = s.indexOf('>');
            s = s.substring(idx + 1).trim();
        }
        if (s.endsWith("</html>")) {
            s = s.substring(0, s.length() - 7).trim();
        }
        if (s.startsWith("<head>")) {
            int endIdx = s.indexOf("</head>");
            s = s.substring(endIdx + 7).trim();
        }
        if (s.startsWith("<body")) {
            int from = s.indexOf('>');
            int to = s.indexOf("</body>");
            s = s.substring(from + 1, to).trim();
        }
        return s;
    }

    public static String[] toLowerCase(String[] value) {
        String[] ret = new String[value.length];
        for (int i = 0; i < value.length; i++) {
            ret[i] = value[i].toLowerCase();
        }
        return ret;
    }

    public static String toString(String message, Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        for (Map.Entry entry : map.entrySet()) {
            sb.append("\n  ").append(entry.getKey()).append(" = ").append(format(entry.getValue()));
        }
        return sb.toString();
    }

    public static String getRootCauseMessage(Exception ex) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            ex = (Exception) cause;
            cause = ex.getCause();
        }
        String message = ex.getMessage();
        if (message == null) {
            message = ex.getClass().getName();
        }
        return message;
    }

    public static String getStackTrace() {
        return getStackTrace(new Exception());
    }

    public static String[] subarray(String[] sa, int beginIndex, int length) {
        String[] result = new String[length];
        arraycopy(sa, beginIndex, result, 0, length);
        return result;
    }

    public static String[] subarray(String[] sa, int beginIndex) {
        return subarray(sa, beginIndex, sa.length - beginIndex);
    }

    public static List<String> tokenizeString(String s) {
        if (s == null) {
            return emptyList();
        }
        StringTokenizer tok = new StringTokenizer(s);
        LinkedList<String> ll = new LinkedList<>();
        while (tok.hasMoreTokens()) {
            ll.add(tok.nextToken());
        }
        return ll;
    }

    public static String[] tokenize(String s) {
        return toStringArray(tokenizeString(s));
    }

    public static String getLastToken(String s, String delimiter) {
        String result = null;
        StringTokenizer tokenizer = new StringTokenizer(s, delimiter);
        while (tokenizer.hasMoreTokens()) {
            result = tokenizer.nextToken();
        }
        return result;
    }

    public static String concat(Object[] sa, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sa.length; i++) {
            sb.append(sa[i]);
            if (i < sa.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static String concat(Object[] sa) {
        return concat(sa, " ");
    }

    public static ArrayList<String> splitWordLineToList(String line, int maxlen) {
        ArrayList<String> al = new ArrayList<>();

        while (line.length() > maxlen) {
            int idx = line.substring(0, maxlen).lastIndexOf(' ');
            if (idx <= 0) {
                idx = maxlen;
            }
            al.add(line.substring(0, idx));
            line = line.substring(idx + 1);
        }
        al.add(line);

        return al;
    }

    public static String trimRight(String text) {
        while (text.endsWith(" ")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    public static List<String> toStringList(String text) {
        BufferedReader in = new BufferedReader(new StringReader(text));
        List<String> lines = new ArrayList<>();
        String line;
        try {
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            LOG.warn("toStringList: Exception", e);
        }
        return lines;
    }

    public static String escapeEscapeSequences(String text) {
        for (String[] esc : ESCAPE_SEQUENCES) {
            text = text.replace(esc[0], esc[1]);
        }
        return text;
    }

}
