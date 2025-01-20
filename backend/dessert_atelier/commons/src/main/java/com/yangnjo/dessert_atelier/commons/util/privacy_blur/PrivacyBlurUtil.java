package com.yangnjo.dessert_atelier.commons.util.privacy_blur;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import com.yangnjo.dessert_atelier.commons.value_type.Address;

public class PrivacyBlurUtil {
    // 이름 마스킹 (앞 두 글자만 표시)
    public static String maskName(String name) {
        if (name == null || name.length() < 3) {
            return name; // 이름이 너무 짧으면 그대로 반환
        }
        String sanitizedString = escape(name);
        return sanitizedString.substring(0, 2) + "*".repeat(name.length() - 2);
    }

    // 주소 마스킹 (숫자만 별표 처리)
    public static String maskAddress(String address) {
        if (address == null || address.isEmpty()) {
            return address;
        }

        String sanitizedAddress = escape(address);
        return sanitizedAddress.replaceAll("\\d", "*");
    }

    // 전화번호 마스킹 (가운데 두 자리와 뒷자리 일부 별표 처리)
    public static String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 13) {
            return phoneNumber; // 기본 형식에 맞지 않으면 그대로 반환
        }
        String sanitizedPhoneNumber = escape(phoneNumber);
        return sanitizedPhoneNumber.replaceAll("(\\d{3}-\\d{2})\\d{2}-(\\d)\\d{2}", "$1**-$2***");
    }

    public static Address maskDestination(Address destination) {
        if (destination == null) {
            return destination;
        }
        return new Address(maskAddress(destination.getPostCode()), "*******", maskName(destination.getReceiver()),
                maskPhoneNumber(destination.getPhone()));
    }

    private static String escape(String input) {
        PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
        return policy.sanitize(input);
    }
}
