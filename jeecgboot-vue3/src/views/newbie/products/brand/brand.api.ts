import { IBrand, IPageParams } from "@/views/newbie/models/entities/product-entity";
import { defHttp } from "/@/utils/http/axios";

enum Api {
	getAllByPage = "/newbie/brand/getAllByPage",
	getById = "/newbie/brand/getById",
	updateById = "/newbie/brand/updateById",
    deleteById = "/newbie/brand/deleteById",
    addBrand = "/newbie/brand/addBrand",
}

/**
 * 查询列表
 * @param params
 */
export async function getBrandList(params: IPageParams) {
	console.log("------------------------------- getBrandList params:", params);
	const obj = await defHttp.get({ url: Api.getAllByPage, params });
    console.log("------------------------------- getBrandList obj:", obj);
    return obj;
}

export function getById(id: string) {
	return defHttp.get({ url: `${Api.getById}/${id}` });
}

/**
 * 修改品牌信息
 * @param params
 * @returns
 */
export function updateBrandById(params: Recordable<IBrand>) {
	console.log("------------------------------- updateBrandById params:", params);
	return defHttp.put({ url: Api.updateById, params });
}

export function deleteById(id: string) {
    console.log("------------------------------- deleteById id:", id);
    return defHttp.delete({ url: `${Api.deleteById}/${id}` });
}

export function addBrand(params: Recordable<IBrand>) {
    console.log("------------------------------- addBrand params:", params);
    return defHttp.post({ url: Api.addBrand, params });
}

