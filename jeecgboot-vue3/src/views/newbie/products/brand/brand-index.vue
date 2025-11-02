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

	<AddBrandModal @register="register" @success="reload" />
</template>

<script lang="ts" name="newbie-product-brand" setup>
import AddBrandModal from "./brand-modal.vue";
import { getBrandList, deleteById } from "./brand.api";
import { columns } from "./brand.data";
import { useModal } from "/@/components/Modal";
import { ActionItem, BasicTable, TableAction } from "/@/components/Table";
import { useListPage } from "/@/hooks/system/useListPage";

/** useListPage 是整个框架的核心用于表格渲染，里边封装了很多公共方法；
 * 平台通过此封装，简化了代码，支持自定义扩展*/
// 通过hook useListPage渲染表格（设置dataSource、columns、actionColumn等参数）
const { tableContext } = useListPage({
	designScope: "newbie-product-brand",
	tableProps: {
		title: "品牌列表",
		api: getBrandList,
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
        showActionColumn: true
	},
});
// BasicTable绑定注册
// const [registerTable] = tableContext;
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

// 修改事件
function handleEdit(record) {
	console.log("修改: ", record);
	openModal(true, {
		record,
		isUpdate: true,
	});
}

// 删除事件
async function handleDelete(record) {
	console.log("删除: ", record);
    await deleteById(record.id);
    reload();
}

// 新增事件
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
