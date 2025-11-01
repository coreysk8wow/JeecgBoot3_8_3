<template>
	<BasicModal
		v-bind="$attrs"
		@register="registerModal"
		:title="getTitle"
		@ok="handleSubmit"
		:helpMessage="['提示1', '提示2']"
	>
		<!-- 自定表单 -->
		<BasicForm @register="registerForm" class="brand-form" />
	</BasicModal>
</template>

<script lang="ts" setup>
import { BasicModal, useModalInner } from "/@/components/Modal";
import { useForm, BasicForm } from "/@/components/Form";
import { ref, unref, computed } from "vue";
import { formSchemas } from "./category.data";
import { getById, updateCategoryById, addCategory } from "./category.api";
import { ICategory } from "/@/views/newbie/models/entities/product-intf";

// 声明Emits
const emit = defineEmits(["success", "register"]);
const isUpdate = ref(true);

const [registerForm, { resetFields, setFieldsValue, getFieldsValue }] = useForm({
	//labelWidth: 150,
	schemas: formSchemas,
	showActionButtonGroup: false,
});

//表单赋值
const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
	//重置表单
	await resetFields();
	setModalProps({ confirmLoading: false });
	isUpdate.value = !!data?.isUpdate;
	if (unref(isUpdate)) {
		//获取详情
		data.record = await getById(data.record.id);
		//表单赋值
		await setFieldsValue({
			...data.record,
		});
	}
});

//设置标题
const getTitle = computed(() => (!unref(isUpdate) ? "新增品类" : "编辑品类"));

//表单提交事件
async function handleSubmit() {
	try {
        const values: Recordable<ICategory> = getFieldsValue();
        setModalProps({ confirmLoading: true });
		if (isUpdate.value) {
			await updateCategoryById(values);
		} else {
            await addCategory(values);
		}
        //关闭弹窗
        closeModal();
        //刷新列表
        emit("success");
	} finally {
		setModalProps({ confirmLoading: false });
	}
}
</script>

<style scoped>
.brand-form {
	margin-top: 50px;
	margin-left: 50px;
}
</style>
