<template>
    <div style="display: flex; align-items: center; justify-content: center; position: relative;">
        <h2 style="position: relative; z-index: 1; margin: 0; font-weight: bold;">样品领用申请单</h2>
        <a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleSubmit"
            style="position: absolute; right: 10px; z-index: 1;">发起流程</a-button>
    </div>

    <div class="color-bar">
        基本信息
    </div>
    <!-- <BasicForm @register="registerForm1" @submit="handleForm1" /> -->
    <div class="color-bar">
        审批内容
    </div>


    <!-- <a-card title="Dynamic Form Example" class="max-w-2xl mx-auto mt-8"> -->
    <!-- <a-form :model="form" layout="vertical"> -->
    <a-button type="primary" @click="addRow">
        + Add Row
    </a-button>
    <br />
    <a-space>
        <template v-for="(item, index) in form.items" :key="item.key">
            <a-row :gutter="16" class="mb-3">
                <a-col :span="4">
                    <a-select v-model:value="item.category" placeholder="Select category">
                        <a-select-option value="A">Category A</a-select-option>
                        <a-select-option value="B">Category B</a-select-option>
                        <a-select-option value="C">Category C</a-select-option>
                    </a-select>
                </a-col>
                <a-col :span="4">
                    <a-select v-model:value="item.category" placeholder="Select category">
                        <a-select-option value="A">Category A</a-select-option>
                        <a-select-option value="B">Category B</a-select-option>
                        <a-select-option value="C">Category C</a-select-option>
                    </a-select>
                </a-col>
                <a-col :span="4">
                    <a-select v-model:value="item.category" placeholder="Select category">
                        <a-select-option value="A">Category A</a-select-option>
                        <a-select-option value="B">Category B</a-select-option>
                        <a-select-option value="C">Category C</a-select-option>
                    </a-select>
                </a-col>

                <a-col :span="4"><a-input v-model:value="item.name" placeholder="Enter name" /></a-col>

                <a-col :span="2" class="flex items-center justify-center">
                    <a-button danger type="link" @click="removeRow(index)" v-if="form.items.length > 1">
                        Remove
                    </a-button>
                </a-col>
            </a-row>
        </template>
    </a-space>

    <!-- <a-form-item> -->
    <!-- <a-button type="primary" @click="addRow">
                + Add Row
            </a-button> -->
    <!-- </a-form-item> -->

    <!--         <a-form-item>
            <a-button type="primary" @click="submitForm">Submit</a-button>
        </a-form-item> -->
    <!-- </a-form> -->
    <!-- </a-card> -->



    <div class="color-bar">
        审批节点设置
    </div>

    <div style="margin-top: 15px; margin-left: 0px;">
        <BasicForm @register="selectUserForm" />
    </div>
</template>

<script lang="ts" name="newbie-applications-sample" setup>
import { useForm, BasicForm } from "/@/components/Form";
import { productFormSchemas, selectUserFormSchemas } from "./sample.data";
import { reactive } from 'vue'
import { message } from 'ant-design-vue'

const [selectUserForm, { getFieldsValue }] = useForm({
    schemas: selectUserFormSchemas,
    showActionButtonGroup: false,
});

/* const [productForm] = useForm({
    schemas: productFormSchemas,
    showActionButtonGroup: false,
});
 */

function handleSubmit() {
    const values = getFieldsValue();
    console.log("----- submit values:", values);
}


// --------------- 动态INPUT 开始-----------------
const form = reactive({
    items: [
        { key: Date.now(), seq: 1, brand: '', category: '', product: '', nBox: 0, nOrder: 0 }
    ]
})

const addRow = () => {
    form.items.push({
        key: Date.now(),
        name: '',
        category: ''
    })
}

const removeRow = (index) => {
    form.items.splice(index, 1)
}

const submitForm = () => {
    console.log('Submitted:', form.items)
    message.success('Form submitted! Check console for details.')
}
// --------------- 动态INPUT 结束-----------------


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

.max-w-2xl {
    max-width: 700px;
}
</style>
