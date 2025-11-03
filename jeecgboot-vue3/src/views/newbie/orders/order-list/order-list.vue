<template>
	<BasicTable @register="registerTable">
		<!-- 搜索区域插槽自定义查询 -->
	</BasicTable>
</template>

<script lang="ts" name="newbie-order-list" setup>
import { orderListColumns, searchFormSchema } from "./order-list.data";
import { BasicTable, SizeType } from "/@/components/Table";
import { useListPage } from "/@/hooks/system/useListPage";
import { getOrdersByPage } from "/@/views/newbie/orders/order-list/order-list.api";

const { tableContext } = useListPage({
	tableProps: {
		title: "订单列表",
		api: getOrdersByPage,
		columns: orderListColumns,
		size: "middle" as SizeType,
		actionColumn: {
			width: 80,
		},
		pagination: {
			pageSize: 10,
		},
        // 是否显示操作列
		showActionColumn: false,
		formConfig: {
			schemas: searchFormSchema,
		},
	},
});
// BasicTable绑定注册
const [registerTable] = tableContext;
</script>

<style scoped></style>
