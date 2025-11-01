import { Nullable } from '@/views/newbie/types/basic-type'
import type { Dayjs } from "dayjs";

export interface IReqItem {
    ordinal: Nullable<string>;
    brandId: Nullable<string>;
    categoryId: Nullable<string>;
    productId: Nullable<string>;
    qtyBox: Nullable<number>;
    qtyOrder: Nullable<number>;
}

export interface IFormData {
    id?: string;
    title: Nullable<string>;
    requester: Nullable<string>;
    reqDept: Nullable<string>;
    reqDate: Nullable<Dayjs>;
    procNo?: Nullable<string>;
    itemCategory: Nullable<number>;
    phone: Nullable<string>;
    items: IReqItem[];
    fileUploadUrl?: Nullable<string>;
    personInChargeId: Nullable<string>;
    personInChargeName?: Nullable<string>;
    status?: Nullable<number>;
    comment?: Nullable<string>;
}
