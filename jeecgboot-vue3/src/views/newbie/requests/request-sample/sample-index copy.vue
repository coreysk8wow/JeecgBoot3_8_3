<template>
    <div style="display: flex; align-items: center; justify-content: center; position: relative;">
        <h2 style="position: relative; z-index: 1; margin: 0; font-weight: bold;">样品领用申请单</h2>
        <a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleAdd"
            style="position: absolute; right: 10px; z-index: 1;">发起流程</a-button>
    </div>

    <div>
        <div class="color-bar">
            基本信息
        </div>

        <BasicForm @register="registerForm" />

        <div class="color-bar">
            审批内容
        </div>

        <div class="color-bar">
            审批节点设置
        </div>
    </div>
</template>

<script lang="ts" name="newbie-applications-sample" setup>
import { useForm, BasicForm } from "/@/components/Form";
import { basicInfoFormSchemas } from "./sample.data";

const [registerForm, { resetFields, setFieldsValue, getFieldsValue }] = useForm({
	//labelWidth: 150,
	schemas: basicInfoFormSchemas,
	showActionButtonGroup: false,
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
}
</style>
