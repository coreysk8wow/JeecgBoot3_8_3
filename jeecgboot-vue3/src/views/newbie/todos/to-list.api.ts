import { message } from "ant-design-vue";
import { IFormData } from "../models/entities/sample-request-intf";
import { defHttp } from "/@/utils/http/axios";
import { IPageParams } from "/@/views/newbie/models/entities/product-intf";

enum Api {
	getTodosByPage = "/newbie/todo/getAllByPage",
	getSampleRequestDetailsById = "/newbie/request/sample/getById",
	updateRequestStatus = "/newbie/request/sample/updateStatus",
	downloadFile = "/newbie/file/downloadFile",
}

export async function getTodosByPage(params: IPageParams) {
	const response = await defHttp.get({ url: Api.getTodosByPage, params });
	console.log("----------response of getTodosByPage:", response);
	return response;
}

export async function getSampleRequestDetailsById(reqId: string) {
	const response = await defHttp.get({ url: `${Api.getSampleRequestDetailsById}/${reqId}` });
	console.log("----------response of getSampleRequestDetailsById:", response);
	return response;
}

export function updateRequestStatus(data: IFormData) {
	return defHttp.put({ url: Api.updateRequestStatus, data });
}

export async function downloadFile(fileUrl: string) {
	try {
        const response = await defHttp.get(
            {
                url: Api.downloadFile,
                params: { fileUrl: fileUrl },
                responseType: "blob",
            },
            {
                // Prevents the axios interceptor from transforming the blob response
                isTransformResponse: false,
                
                // Returns the raw axios response object
                isReturnNativeResponse: true,
            }
        );

        console.log("文件下载响应：", response);
        return response.data;
	} catch (error) {
		console.error("---文件下载失败:", error);
		message.error("文件下载失败。");
	}
}
