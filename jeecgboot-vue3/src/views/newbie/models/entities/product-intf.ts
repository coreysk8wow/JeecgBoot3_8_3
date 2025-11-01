import { Nullable } from '@/views/newbie/types/basic-type'

export interface IBrand {
    id: Nullable<string>;
    name: Nullable<string>;
    createTime?: Nullable<Date>;
    updateTime?: Nullable<Date>;
}

export interface ICategory {
    id: Nullable<string>;
    name: Nullable<string>;
    createTime?: Nullable<Date>;
    updateTime?: Nullable<Date>;
}

export interface IProduct {
    id: Nullable<string>;
    name: Nullable<string>;
    brandId: Nullable<string>;
    categoryId: Nullable<string>;
    createTime?: Nullable<Date>;
    updateTime?: Nullable<Date>;
    isDeleted?: Nullable<number>;
}

export interface IPageParams {
    pageNo: number;
    pageSize: number;
}