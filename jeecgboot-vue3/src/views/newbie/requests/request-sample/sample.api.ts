import { IFormData } from "../../models/entities/sample-request-intf";
import { defHttp } from "/@/utils/http/axios";

enum Api {
	getAllBrands = "/newbie/brand/getAll",
	getAllCategories = "/newbie/category/getAll",
	getProductsByCategoryAndBrand = "/newbie/product/getByCategoryAndBrand",
	submitRequest = "/newbie/request/sample/save",
	uploadFile = "/newbie/file/uploadFile",
}

export function getAllBrands() {
	return defHttp.get({ url: Api.getAllBrands });
}

export function getAllCategories() {
	return defHttp.get({ url: Api.getAllCategories });
}

export function getProductsByCategoryAndBrand(categoryId: string, brandId: string) {
	return defHttp.get({
		url: `${Api.getProductsByCategoryAndBrand}/${categoryId}/${brandId}`,
	});
}

export function submitRequest(data: IFormData) {
	return defHttp.post({ url: Api.submitRequest, data });
}

export async function uploadFile(file: File) {
	return defHttp.uploadFile(
        { url: Api.uploadFile }, 
        { file: file }, 
        { isReturnResponse: true }
    );
}
