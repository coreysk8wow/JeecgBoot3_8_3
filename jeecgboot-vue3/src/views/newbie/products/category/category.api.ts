import { defHttp } from "/@/utils/http/axios";
import { ICategory, IPageParams } from "/@/views/newbie/models/entities/product-intf";

enum Api {
	getAllByPage = "/newbie/category/getAllByPage",
	getById = "/newbie/category/getById",
	updateById = "/newbie/category/updateById",
    deleteById = "/newbie/category/deleteById",
    addCategory = "/newbie/category/addCategory",
}

export function getCategoryList(params: IPageParams) {
	console.log("------------------------------- getCategoryList params:", params);
	return defHttp.get({ url: Api.getAllByPage, params });
}

export function getById(id: string) {
	return defHttp.get({ url: `${Api.getById}/${id}` });
}

export function updateCategoryById(params: Recordable<ICategory>) {
	console.log("------------------------------- updateCategoryById params:", params);
	return defHttp.put({ url: Api.updateById, params });
}

export function deleteById(id: string) {
    console.log("------------------------------- deleteById id:", id);
    return defHttp.delete({ url: `${Api.deleteById}/${id}` });
}

export function addCategory(params: Recordable<ICategory>) {
    console.log("------------------------------- addCategory params:", params);
    return defHttp.post({ url: Api.addCategory, params });
}

