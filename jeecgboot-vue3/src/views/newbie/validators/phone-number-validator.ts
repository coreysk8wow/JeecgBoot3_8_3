/**
 * Common phone number regular expressions and validator
 * File: src/views/newbie/regularExpressions/common-regs.ts
 */

/** E.164 international format (e.g. +14155552671) */
export const REGEX_E164 = /^\+?[1-9]\d{1,14}$/;

/** China mainland mobile numbers (11 digits, starts with 13-19) */
export const REGEX_CN_MOBILE = /^1[3-9]\d{9}$/;

/** Generic flexible phone matcher allowing country code, separators, parentheses */
export const REGEX_GENERIC = /^(?:\+?\d{1,3}[-.\s]?)?(?:\(?\d{2,4}\)?[-.\s]?)?\d{3,4}[-.\s]?\d{3,4}$/;

/**
 * Validate a phone number.
 * @param phone - input phone string
 * @param mode - validation mode: 'generic' (default), 'e164' or 'cn'
 * @returns true if valid for the chosen mode
 */
export function validatePhone(
    phone: string,
    mode: 'generic' | 'e164' | 'cn' = 'generic'
): boolean {
    if (typeof phone !== 'string') return false;
    const v = phone.trim();
    if (!v) return false;

    switch (mode) {
        case 'e164':
            return REGEX_E164.test(v);
        case 'cn':
            return REGEX_CN_MOBILE.test(v);
        default:
            return REGEX_GENERIC.test(v);
    }
}
