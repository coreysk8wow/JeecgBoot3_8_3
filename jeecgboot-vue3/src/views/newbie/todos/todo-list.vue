<template>
	<BasicTable @register="registerTable">
		<!--操作栏-->
		<template #action="{ record }">
			<TableAction :actions="getTableAction(record)" />
		</template>
	</BasicTable>

	<TodoDetailsModal @register="register" @success="reload" />
</template>

<script lang="ts" name="newbie-todo-list" setup>
import { RequestStatusEnum } from "../enums/request-enum";
import { getTodosByPage } from "./to-list.api";
import { todoListColumns } from "./todo-list.data";
import TodoDetailsModal from "./todo-show-details-modal.vue";
import { useModal } from "/@/components/Modal";
import { ActionItem, BasicTable, SizeType, TableAction } from "/@/components/Table";
import { useListPage } from "/@/hooks/system/useListPage";

const { tableContext } = useListPage({
	tableProps: {
		title: "待办事项列表",
		api: getTodosByPage,
		columns: todoListColumns,
		size: "middle" as SizeType,
		actionColumn: {
			width: 80,
		},
		pagination: {
			pageSize: 3,
		},
        showActionColumn: true
	},
});
// BasicTable绑定注册
const [registerTable, { reload }] = tableContext;

// Modal绑定注册
const [register, { openModal }] = useModal();

/**
 * 操作栏
 */
function getTableAction(record: any): ActionItem[] {
	const status = record.statusCode;
	const labelContent = status === RequestStatusEnum.PENDING.code ? "审批" : "查看";

	return [
		{
			label: labelContent,
			onClick: handleShowDetails.bind(null, record),
		},
	];
}

async function handleShowDetails(record: any) {
	/* 	// Call backend to get request details by record.reqId.
	const categoryId = record.categoryId;
	if (
		categoryId !== null &&
		categoryId !== undefined &&
		RequestCategoryEnum.SAMPLE_REQUEST === RequestCategoryEnum.fromCode(categoryId)
	) {
		// Call 
	}
 */

	openModal(true, {
		statusCode: record.statusCode,
		// categoryId: record.categoryId,
		reqId: record.reqId,
	});
}
</script>

<style scoped></style>
