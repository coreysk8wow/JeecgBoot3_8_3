import { IPageParams, IProduct } from "@/views/newbie/models/entities/product-entity";
import { defHttp } from "/@/utils/http/axios";

enum Api {
    getAllByPage = "/newbie/product/getAllByPage",
    getById = "/newbie/product/getById",
    updateById = "/newbie/product/updateById",
    deleteById = "/newbie/product/deleteById",
    addProduct = "/newbie/product/addProduct",
    getAllBrands = "/newbie/brand/getAll",
    getAllCategories = "/newbie/category/getAll",
}

/**
 * 查询列表
 * @param params
 */
export async function getProductList(params: IPageParams) {
    console.log("------------------------------- getProductList params:", params);
    const obj = await defHttp.get({ url: Api.getAllByPage, params });
    console.log("------------------------------- getProductList obj:", obj);
    return obj;
}

/* export function getById(id: string) {
    return defHttp.get({ url: `${Api.getById}/${id}` });
} */

export function updateProductById(params: IProduct) {
    console.log("------------------------------- updateProductById params:", params);
    return defHttp.put({ url: Api.updateById, params });
}

export function deleteById(id: string) {
    console.log("------------------------------- deleteById params:", id);
    return defHttp.delete({ url: `${Api.deleteById}/${id}` });
}

export function addProduct(params: IProduct) {
    console.log("------------------------------- addProduct params:", params);
    return defHttp.post({ url: Api.addProduct, params });
}

export function getAllBrands() {
    return defHttp.get({ url: Api.getAllBrands });
}

export function getAllCategories() {
    return defHttp.get({ url: Api.getAllCategories });
}
