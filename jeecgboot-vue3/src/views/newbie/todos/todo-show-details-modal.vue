// 显示申请单详情，如果是来自待办页面，显示 通过/拒绝 按钮
<template>
	<BasicModal v-bind="$attrs" @register="registerModal" title="申请单详情" @ok="handleSubmit">
		<div
			style="
				display: flex;
				align-items: center;
				justify-content: center;
				position: relative;
				margin-top: 10px;
			"
		>
			<h2 style="position: relative; z-index: 1; margin: 0; font-weight: bold">
				样品领用申请单
			</h2>
		</div>

		<div class="color-bar">基本信息</div>

		<div style="margin-top: 10px; margin-left: 0px; width: 100%">
			<div
				style="
					margin-top: 15px;
					margin-left: 20px;
					width: 100%;
					display: flex;
					align-items: center;
				"
			>
				<span style="width: 80px; text-align: right; margin-right: 10px"> 标题: </span>
				<!-- <a-input :value="tableData.title" style="width: 70%" disabled /> -->
				<span style="width: 70%">{{ tableData.title }}</span>
			</div>

			<div
				style="
					margin-top: 15px;
					margin-left: 20px;
					width: 100%;
					display: flex;
					align-items: center;
				"
			>
				<span style="width: 80px; text-align: right; margin-right: 10px"> 申请人: </span>
				<!-- <a-input
					:value="tableData.requester"
					style="width: 30%; margin-right: 20px"
					disabled
				/> -->
				<span style="width: 30%; margin-right: 20px">{{ tableData.requester }}</span>

				<span style="width: 100px; text-align: right; margin-right: 10px"> 申请部门: </span>
				<!-- <a-input :value="tableData.reqDept" style="width: 30%" disabled /> -->
				<span style="width: 30%">{{ tableData.reqDept }}</span>
			</div>

			<div
				style="
					margin-top: 15px;
					margin-left: 20px;
					width: 100%;
					display: flex;
					align-items: center;
				"
			>
				<span style="width: 80px; text-align: right; margin-right: 10px"> 申请日期: </span>
				<!-- <a-date-picker :value="tableData.reqDate" disabled /> -->
				<span style="width: 32%">{{ dayjs(tableData.reqDate).format("YYYY-MM-DD") }}</span>

				<span style="width: 100px; text-align: right; margin-right: 10px"> 流程编号: </span>
				<!-- <a-input :value="tableData.procNo" style="width: 30%" disabled /> -->
				<span style="width: 30%">{{ tableData.procNo }}</span>
			</div>
		</div>

		<div class="color-bar">审批内容</div>

		<div
			style="
				margin-top: 15px;
				margin-left: 20px;
				width: 100%;
				display: flex;
				align-items: center;
			"
		>
			<span style="width: 80px; text-align: right; margin-right: 10px"> 领用类型: </span>
			<!-- <a-select
				:value="tableData.itemCategory"
				:options="itemCategoryOptions"
				style="width: 30%; margin-right: 20px"
				disabled
			/> -->
			<span style="width: 30%; margin-right: 20px">
				{{
					itemCategoryOptions.find((option) => option.value === tableData.itemCategory)
						?.label
				}}
			</span>

			<span style="width: 100px; text-align: right; margin-right: 10px"> 联系电话: </span>
			<!-- <a-input :value="tableData.phone" style="width: 30%" disabled /> -->
			<span style="width: 30%">{{ tableData.phone }}</span>
		</div>

		<a-table
			bordered
			:data-source="tableData.items"
			:columns="requestSampleItemsColumns"
			style="margin-top: 20px"
		>
		</a-table>

		<div class="color-bar">附件</div>

		<div style="margin-top: 15px; margin-left: 20px">
			<!-- <a-button type="primary" @click="downloadFile"> 下载附件 </a-button> -->

			<template v-if="tableData.fileUploadUrl">
				<a-button type="primary" @click="download" > 下载附件 </a-button>
				<span style="margin-left: 10px">
					{{ attachmentFileName }}
				</span>
			</template>
			<template v-else>
				<span>暂无附件</span>
			</template>
		</div>

		<div class="color-bar">审批节点设置</div>

		<div style="margin-top: 15px; margin-left: 20px">
			<span>审批人： {{ tableData.personInChargeName }}</span>
		</div>

		<div
			style="
				margin-top: 15px;
				margin-left: 20px;
				width: 100%;
				display: flex;
				align-items: center;
			"
		>
			<div>
				<a-textarea
					v-model:value="tableData.comment"
					placeholder="审批意见，最多100字。"
					:rows="4"
					:maxlength="100"
					style="width: 400px"
					:disabled="!isUpdate"
				/>
			</div>
			<div
				v-if="isUpdate"
				style="display: flex; flex-direction: column; gap: 10px; margin-left: 30px"
			>
				<a-button type="primary" @click="handleApprove"> 同意 </a-button>
				<a-button type="primary" danger @click="handleReject"> 拒绝 </a-button>
			</div>
		</div>
	</BasicModal>
</template>

<script lang="ts" setup>
import { BasicModal, useModalInner } from "/@/components/Modal";
import { computed, Ref, ref } from "vue";
import { message } from "ant-design-vue";
import { downloadFile, getSampleRequestDetailsById, updateRequestStatus } from "./to-list.api";
import { itemCategoryOptions } from "@/views/newbie/requests/request-sample/sample.data";
import { IFormData, IReqItem } from "../models/entities/sample-request-intf";
import dayjs from "dayjs";
import { RequestStatusEnum } from "../enums/request-enum";
import { requestSampleItemsColumns } from "./todo-list.data";

// 声明Emits
const emit = defineEmits(["success", "register"]);

const reqId: Ref<string> = ref("");
const statusCode: Ref<string> = ref("");
const tableData: Ref<IFormData> = ref({
	title: undefined,
	requester: undefined,
	reqDept: undefined,
	reqDate: undefined,
	procNo: undefined,
	itemCategory: undefined,
	phone: undefined,
	items: [] as IReqItem[],
	fileUploadUrl: undefined,
	personInChargeName: undefined,
	personInChargeId: undefined,
	status: undefined,
	comment: undefined,
});
const isUpdate = ref();

const attachmentFileName = computed(() => {
	if (tableData.value.fileUploadUrl) {
		const url = tableData.value.fileUploadUrl;
		const idx = url.indexOf("_");
		return url.substring(idx + 1);
	}
	return "文件名未知";
});

//表单赋值
const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
	setModalProps({
		confirmLoading: false,
		width: 1000,
		centered: true,
		showOkBtn: false,
		showCancelBtn: false,
	});

	reqId.value = data.reqId;
	statusCode.value = data.statusCode;
	console.log("申请单详情弹窗数据：", data);

	// 调用接口获取申请单详情
	const response = await getSampleRequestDetailsById(reqId.value);
	if (response) {
		// 将返回的数据赋值给表格
		tableData.value = response;
		tableData.value.reqDate = dayjs(response.reqDate);
	}

	console.log(" ---------- tableData in todo-show-details-modal:", tableData.value);

	isUpdate.value = Number(statusCode.value) === RequestStatusEnum.PENDING.code ? true : false;
});

/**
 * 点击提交按钮的value值
 * @param values
 */
function handleSubmit(isApproved: boolean) {
	console.log("提交申请单详情表单");

	const updatedData: IFormData = {
		id: reqId.value,
		title: undefined,
		requester: undefined,
		reqDept: undefined,
		reqDate: undefined,
		procNo: undefined,
		itemCategory: undefined,
		phone: undefined,
		items: [] as IReqItem[],
		fileUploadUrl: undefined,
		personInChargeName: undefined,
		personInChargeId: undefined,
		status: isApproved ? RequestStatusEnum.APPROVED.code : RequestStatusEnum.REJECTED.code,
		comment: tableData.value.comment,
	};

	updateRequestStatus(updatedData);

	// 刷新列表
	emit("success");
}

function handleApprove() {
	message.success("您已同意该申请单！");
	handleSubmit(true);

	//关闭弹窗
	closeModal();
	//刷新列表
	emit("success");
}

function handleReject() {
	message.success("您已拒绝该申请单！");
	handleSubmit(false);

	//关闭弹窗
	closeModal();
	//刷新列表
	emit("success");
}

async function download() {
	const fileUrl = tableData.value.fileUploadUrl;
    console.log("下载附件，文件URL：", fileUrl);

	if (!fileUrl) {
		message.error("没有可下载的附件！");
		return;
	}
	try {
		const response = await downloadFile(fileUrl);
        if (!response) {
            message.error("文件下载失败，请检查文件是否存在");
            return;
        }

        console.log("文件下载响应：", response);

		// 创建 blob URL
		const blob = new Blob([response]);
		const url = window.URL.createObjectURL(blob);

		// 从 fileUrl 中提取文件名
		// const filename = fileUrl.split("/").pop() || "download";
        const filename = fileUrl.substring(fileUrl.indexOf("_") + 1);

		// 创建临时 <a> 标签并触发下载
		const link = document.createElement("a");
		link.href = url;
		link.download = filename;
		document.body.appendChild(link);
		link.click();

		// 清理
		document.body.removeChild(link);
		window.URL.revokeObjectURL(url);

		message.success("文件下载成功");
	} catch (error) {
		console.error("文件下载失败:", error);
		message.error("文件下载失败，请检查文件是否存在");
		// throw error;
	}
}
</script>

<style scoped>
.brand-form {
	margin-top: 50px;
	margin-left: 50px;
}

.space {
	margin-top: 50px;
	margin-left: 50px;
}

.required-star {
	color: red;
	margin-right: 5px;
}

.input-width {
	width: 300px;
}

.color-bar {
	/* position: absolute; */
	left: 0;
	width: 100%;
	height: 32px;
	background: #52c41a;
	z-index: 0;
	display: flex;
	align-items: center;
	padding-left: 10px;
	font-weight: bold;
	margin-top: 10px;
}
</style>
