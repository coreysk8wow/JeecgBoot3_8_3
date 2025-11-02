<template>
	<div
		style="
			display: flex;
			align-items: center;
			justify-content: center;
			position: relative;
			margin-top: 10px;
		"
	>
		<h2 style="position: relative; z-index: 1; margin: 0; font-weight: bold">样品领用申请单</h2>
		<a-button
			type="primary"
			preIcon="ant-design:plus-outlined"
			@click="handleSubmit"
			style="position: absolute; right: 10px; z-index: 1"
			>发起流程</a-button
		>
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
			<span style="width: 80px; text-align: right; margin-right: 10px">
				<span class="required-star">*</span>标题:
			</span>
			<a-input v-model:value="formData.title" style="width: 70%" />
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
			<span style="width: 80px; text-align: right; margin-right: 10px">
				<span class="required-star">*</span>申请人:
			</span>
			<a-input v-model:value="formData.requester" style="width: 30%; margin-right: 20px" />

			<span style="width: 100px; text-align: right; margin-right: 10px">
				<span class="required-star">*</span>申请部门:
			</span>
			<a-input v-model:value="formData.reqDept" style="width: 30%" />
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
			<span style="width: 80px; text-align: right; margin-right: 10px">
				<span class="required-star">*</span>申请日期:
			</span>
			<a-date-picker v-model:value="formData.reqDate" :disabled-date="disabledDate" />

			<!-- <a-input v-model:value="formData.reqDate" style="width: 30%; margin-right: 20px" /> -->

			<!-- <span style="width: 100px; text-align: right; margin-right: 10px;">
                <span class="required-star">*</span>流程编号:
            </span>
            <a-input v-model:value="formData.procNo" style="width: 30%" /> -->
		</div>
	</div>

	<div class="color-bar">审批内容</div>

	<div
		style="margin-top: 15px; margin-left: 20px; width: 100%; display: flex; align-items: center"
	>
		<span style="width: 80px; text-align: right; margin-right: 10px">
			<span class="required-star">*</span>领用类型:
		</span>
		<a-select
			v-model:value="formData.itemCategory"
			:options="itemCategoryOptions"
			style="width: 30%; margin-right: 20px"
		/>

		<span style="width: 100px; text-align: right; margin-right: 10px">
			<span class="required-star">*</span>联系电话:
		</span>
		<a-input v-model:value="formData.phone" style="width: 30%" />
	</div>

	<!-- 申请物品清单，动态添加行 -->
	<div
		style="
			display: flex;
			justify-content: flex-end;
			margin-right: 10px;
			margin-top: 10px;
			margin-bottom: 5px;
		"
	>
		<a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleAdd"
			>增加</a-button
		>
	</div>
	<a-table bordered :data-source="reqItemsDataSource" :columns="columns">
		<template #bodyCell="{ column, record }">
			<template v-if="column.dataIndex === 'brand'">
				<a-select
					v-model:value="record.brandId"
					:options="brandOptions"
					@change="handleBrandChange(record)"
					style="width: 100%"
				/>
			</template>
			<template v-if="column.dataIndex === 'category'">
				<a-select
					v-model:value="record.categoryId"
					:options="categoryOptions"
					@change="handleCategoryChange(record)"
					style="width: 100%"
				/>
			</template>
			<template v-if="column.dataIndex === 'product'">
				<a-select
					v-model:value="record.productId"
					style="width: 100%"
					:options="productOptions"
					:disabled="!record.brandId || !record.categoryId || productOptions.length === 0"
				/>
			</template>
			<template v-if="column.dataIndex === 'qtyBox'">
				<a-input v-model:value="record.qtyBox" />
			</template>
			<template v-if="column.dataIndex === 'qtyOrder'">
				<a-input v-model:value="record.qtyOrder" />
			</template>

			<template v-else-if="column.dataIndex === 'operation'">
				<a-popconfirm
					v-if="reqItemsDataSource.length"
					title="确定删除?"
					@confirm="onDelete(record.ordinal)"
				>
					<a>删除</a>
				</a-popconfirm>
			</template>
		</template>
	</a-table>

	<div>
		<!-- <BasicForm @register="uploadFileForm" /> -->

		<a-upload
			:customRequest="handleFileUpload"
			:multiple="false"
			:beforeUpload="beforeUpload"
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
			style="display: flex; justify-content: flex-end; margin-top: 10px; margin-right: 10px"
            
            :fileList="filesUrl"
			:url="uploadUrl"
		>
			<a-button type="primary" preIcon="ant-design:plus-outlined">
				<!-- <upload-outlined></upload-outlined> -->
				附件上传
			</a-button>
		</a-upload>

		<span style="color: red"
			>说明：
			请将规格明细（数量/份数）等信息以附件形式上传，以便开具酒单，附件大小不能超过10M， 附件格式仅限xlsx。</span
		>
	</div>

	<div class="color-bar">审批节点设置</div>

	<div style="margin-top: 15px; margin-left: 0px; margin-bottom: 30px;">
		<BasicForm @register="selectUserForm" />
        <span style="color: red">
            注意：仅支持选一个审核人，选多个会报错。
        </span>
    </div>
</template>

<script lang="ts" name="newbie-request-sample" setup>
import { useForm, BasicForm } from "/@/components/Form";
import { selectUserFormSchemas } from "./sample.data";
import { ref, computed, onMounted, unref } from "vue";
import type { Ref } from "vue";
import {
	columns,
	itemCategoryOptions,
	brandOptions,
	categoryOptions,
	productOptions,
} from "./sample.data";
import { IFormData, IReqItem } from "@/views/newbie/models/entities/sample-request-intf";
import {
	getAllBrands,
	getAllCategories,
	getProductsByCategoryAndBrand,
	submitRequest,
} from "./sample.api";
import type { Dayjs } from "dayjs";
import dayjs from "dayjs";
import { message } from "ant-design-vue";
import { uploadFile } from "./sample.api";
import { router } from "/@/router";

const [selectUserForm, { getFieldsValue }] = useForm({
	schemas: selectUserFormSchemas,
	showActionButtonGroup: false,
});

function handleSubmit() {
	const values = getFieldsValue();
	console.log("----- submit values:", values);
	console.log("----- dataSource:", reqItemsDataSource.value);

	if (!validateFormData()) {
		return;
	}

	const data: IFormData = unref(formData);

	data.personInChargeId = values.name;
	data.items = reqItemsDataSource.value;
    
	console.log("----提交的表单数据:", data);

	submitRequest(data)
        .then(() => {
            console.log("样品领用申请单 - 提交成功");
            
            // Redirect to complete page
            // window.location.href = "/newbie/requests/done";
            router.push({
                name: 'newbie-requests-request-complete',
            });
		})
		.catch((error) => {
			console.error("样品领用申请单 - 提交失败:", error);
			message.error("提交失败，请稍后重试。");
		});
}

function validateFormData(): boolean {
	// 简单验证示例
	if (!formData.value.title) {
		message.error("标题不能为空");
		return false;
	}
	if (!formData.value.requester) {
		message.error("申请人不能为空");
		return false;
	}
	if (!formData.value.reqDept) {
		message.error("申请部门不能为空");
		return false;
	}
	if (!formData.value.reqDate) {
		message.error("申请日期不能为空");
		return false;
	}
	if (!formData.value.itemCategory) {
		message.error("领用类型不能为空");
		return false;
	}
	if (!formData.value.phone) {
		message.error("联系电话不能为空");
		return false;
	}
	if (reqItemsDataSource.value.length === 0) {
		message.error("申请物品清单不能为空");
		return false;
	} else if (
		reqItemsDataSource.value.some(
			(item) =>
				!item.brandId ||
				!item.categoryId ||
				!item.productId ||
				!item.qtyBox ||
				!item.qtyOrder ||
				item.qtyBox <= 0 ||
				item.qtyOrder <= 0,
		)
	) {
		message.error("申请物品清单中品牌、品类和产品不能为空, 数量必须大于0。");
		return false;
	}
	return true;
}

// --------------- 动态INPUT 开始-----------------

// 整个表单的数据
const formData: Ref<IFormData> = ref({
	title: undefined,
	requester: undefined,
	reqDept: undefined,
    reqDate: dayjs(),
	// procNo: undefined,
	itemCategory: undefined,
	phone: undefined,
	items: [],
	fileUploadUrl: undefined,
	personInChargeId: undefined,
});
const reqItemsDataSource: Ref<IReqItem[]> = ref([]);

const count = computed(() => reqItemsDataSource.value.length + 1);

function disabledDate(datePicked: Dayjs) {
	// Can not select days before today and today
	return datePicked < dayjs().startOf("day");
}

const onDelete = (ordinal: string) => {
	reqItemsDataSource.value = reqItemsDataSource.value.filter((item) => item.ordinal !== ordinal);

	// 重新计算ordinal，使其连续
	reqItemsDataSource.value = reqItemsDataSource.value.map((item, index) => ({
		...item,
		ordinal: (index + 1).toString(),
	}));
};
const handleAdd = () => {
    // console.log("-------- count.value:", count.value.toString());
	const newData: IReqItem = {
		ordinal: count.value.toString(),
		brandId: undefined,
		categoryId: undefined,
		productId: undefined,
		qtyBox: 1,
		qtyOrder: 1,
	};
	reqItemsDataSource.value.push(newData);
};

const filesUrl = ref();
const uploadUrl = ref();

// 验证上传文件大小
function beforeUpload(file) {
	const isLt10M = file.size / 1024 / 1024 < 10;
	if (!isLt10M) {
		message.error("附件大小不能超过10M!");
		return false;
	}
	return true;
};

async function handleFileUpload(options) {
	const { file, onSuccess, onError } = options;

	try {
		const response = await uploadFile(file);
        console.log("response-----", response);
        formData.value.fileUploadUrl = response.result.url;
		onSuccess(response);
	} catch (error) {
		console.error("上传失败:", error);
		onError(error);
	}
}

async function handleBrandChange(record) {
	record.productId = undefined; // 重置已选择的产品

	// 如果选择了品类
	if (record.categoryId) {
		const products = await getProductsByCategoryAndBrand(record.categoryId, record.brandId);
		productOptions.value = products.map((p) => ({ label: p.name, value: p.id }));
	}
}

async function handleCategoryChange(record) {
	record.productId = undefined; // 重置已选择的产品

	// 如果选择了品牌
	if (record.brandId) {
		const products = await getProductsByCategoryAndBrand(record.categoryId, record.brandId);
		productOptions.value = products.map((p) => ({ label: p.name, value: p.id }));
	}
}

onMounted(async () => {
	// 初始化时添加一行
	handleAdd();

	// 加载品牌下拉框的选项
	const brands = await getAllBrands();
	brandOptions.value = brands.map((b) => ({ label: b.name, value: b.id }));

	// 加载品类下拉框的选项
	const categories = await getAllCategories();
	categoryOptions.value = categories.map((c) => ({ label: c.name, value: c.id }));
});
</script>

<style scoped>
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

.required-star {
	color: red;
	margin-right: 5px;
}
</style>
