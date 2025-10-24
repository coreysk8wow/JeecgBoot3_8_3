<template>
	<a-button
		type="primary"
		preIcon="ant-design:plus-outlined"
		@click="handleAdd"
		class="add-button"
	>
		新增
	</a-button>

	<!--定义表格-->
	<BasicTable @register="registerTable">
		<!--操作栏-->
		<template #action="{ record }">
			<TableAction :actions="getTableAction(record)" />
		</template>
	</BasicTable>

	<AddProductModal @register="register" @success="reload" />
</template>

<script lang="ts" name="newbie-product-product" setup>
import { ActionItem, BasicTable, TableAction } from "/@/components/Table";
import { useListPage } from "/@/hooks/system/useListPage";
import { useModal } from "/@/components/Modal";
import AddProductModal from "./product-modal.vue";
import { columns } from "./product.data";
import { getProductList, deleteById } from "./product.api";

const { tableContext } = useListPage({
	designScope: "newbie-product-product",
	tableProps: {
		title: "产品列表",
		api: getProductList,
		columns: columns,
		size: "small",
		actionColumn: {
			width: 120,
		},
		// 分页配置
		pagination: {
			pageSize: 2,
		},
        defSort: {
			column: "createTime",
			order: "ascend",
		},
	},
});
// BasicTable绑定注册
const [registerTable, { reload }] = tableContext;

// Modal绑定注册
const [register, { openModal }] = useModal();

/**
 * 操作栏
 */
function getTableAction(record): ActionItem[] {
	return [
		{
			label: "修改",
			onClick: handleEdit.bind(null, record),
		},
		{
			label: "删除",
			popConfirm: {
				title: "是否确认删除",
				confirm: handleDelete.bind(null, record),
			},
		},
	];
}

function handleEdit(record) {
	console.log("编辑:", record);
    openModal(true, {
		record,
		isUpdate: true,
	});
}

async function handleDelete(record) {
	console.log("删除:", record);

	await deleteById(record.productId);
    reload();
}

function handleAdd() {
	openModal(true, {
		isUpdate: false,
	});
}
</script>

<style scoped>
.add-button {
	margin-top: 10px;
	margin-left: 10px;
}
</style>
