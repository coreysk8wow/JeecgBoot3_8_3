/* export enum RequestStatus {
	PENDING = "0",
	APPROVED = "1",
	REJECTED = "2",
} */



export class RequestStatusEnum {
    static readonly PENDING = new RequestStatusEnum(0, "待办");
    static readonly APPROVED = new RequestStatusEnum(1, "已通过");
    static readonly REJECTED = new RequestStatusEnum(2, "已拒绝");

    #code: number;
    #name: string;

    private constructor(code: number, desc: string) {
        this.#code = code;
        this.#name = desc;
    }

    get code(): number {
        return this.#code;
    }

    get name(): string {
        return this.#name;
    }

    static fromCode(code: number): RequestStatusEnum | null {
        switch (code) {
            case RequestStatusEnum.PENDING.code:
                return RequestStatusEnum.PENDING;
            case RequestStatusEnum.APPROVED.code:
                return RequestStatusEnum.APPROVED;
            case RequestStatusEnum.REJECTED.code:
                return RequestStatusEnum.REJECTED;
            default:
                return null;
        }
    }

    toJSON() {
        return {
            code: this.#code,
            desc: this.#name
        };
    }

    toString(): string {
        return JSON.stringify(this.toJSON());
    }
}

export class RequestCategoryEnum {
    static readonly SAMPLE_REQUEST = new RequestCategoryEnum(1, "样品领用申请单");

    #code: number;
    #name: string;

    private constructor(code: number, desc: string) {
        this.#code = code;
        this.#name = desc;
    }

    get code(): number {
        return this.#code;
    }

    get name(): string {
        return this.#name;
    }

    static fromCode(code: number): RequestCategoryEnum | null {
        switch (code) {
            case RequestCategoryEnum.SAMPLE_REQUEST.code:
                return RequestCategoryEnum.SAMPLE_REQUEST;
            default:
                return null;
        }
    }

    toJSON() {
        return {
            code: this.#code,
            desc: this.#name
        };
    }

    toString(): string {
        return JSON.stringify(this.toJSON());
    }
}