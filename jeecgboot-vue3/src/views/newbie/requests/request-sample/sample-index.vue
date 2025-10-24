<template>
    <div style="display: flex; align-items: center; justify-content: center; position: relative;margin-top: 10px;">
        <h2 style="position: relative; z-index: 1; margin: 0; font-weight: bold;">样品领用申请单</h2>
        <a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleSubmit"
            style="position: absolute; right: 10px; z-index: 1;">发起流程</a-button>
    </div>

    <div class="color-bar">
        基本信息
    </div>

    <div style="margin-top: 10px; margin-left: 0px; width: 100%;">
        <div style="margin-top: 15px; margin-left: 20px; width: 100%; display: flex; align-items: center;">
            <span style="width: 80px; text-align: right; margin-right: 10px;">
                <span class="required-star">*</span>标题:
            </span>
            <a-input v-model:value="formData.title" style="width: 70%" />
        </div>

        <div style="margin-top: 15px; margin-left: 20px; width: 100%; display: flex; align-items: center;">
            <span style="width: 80px; text-align: right; margin-right: 10px;">
                <span class="required-star">*</span>申请人:
            </span>
            <a-input v-model:value="formData.requester" style="width: 30%; margin-right: 20px;" />

            <span style="width: 100px; text-align: right; margin-right: 10px;">
                <span class="required-star">*</span>申请部门:
            </span>
            <a-input v-model:value="formData.reqDept" style="width: 30%" />
        </div>

        <div style="margin-top: 15px; margin-left: 20px; width: 100%; display: flex; align-items: center;">
            <span style="width: 80px; text-align: right; margin-right: 10px;">
                <span class="required-star">*</span>申请日期:
            </span>
            <a-input v-model:value="formData.reqDate" style="width: 30%; margin-right: 20px;" />

            <!-- <span style="width: 100px; text-align: right; margin-right: 10px;">
                <span class="required-star">*</span>流程编号:
            </span>
            <a-input v-model:value="formData.procNo" style="width: 30%" /> -->
        </div>
    </div>

    <div class="color-bar">
        审批内容
    </div>

    <div style="margin-top: 15px; margin-left: 20px; width: 100%; display: flex; align-items: center;">
        <span style="width: 80px; text-align: right; margin-right: 10px;">
            <span class="required-star">*</span>领用类型:
        </span>
        <!-- <a-input v-model:value="formData.itemCategory" style="width: 30%; margin-right: 20px;" /> -->
        <a-select v-model:value="formData.itemCategory" :options="itemCategoryOptions"
            style="width: 30%; margin-right: 20px;" />

        <span style="width: 100px; text-align: right; margin-right: 10px;">
            <span class="required-star">*</span>联系电话:
        </span>
        <a-input v-model:value="formData.phone" style="width: 30%" />
    </div>


    <!-- 申请物品清单，动态添加行 -->
    <div style="display: flex; justify-content: flex-end; margin-right: 10px; margin-top: 10px; margin-bottom: 5px;">
        <a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleAdd">增加</a-button>
    </div>
    <a-table bordered :data-source="reqItemsDataSource" :columns="columns">
        <template #bodyCell="{ column, record }">
            <template v-if="column.dataIndex === 'brandName'">
                <a-select v-model:value="reqItemsDataSource[record.seq - 1].brandName" style="width: 100%"
                    :options="brandOptions" />
            </template>
            <template v-if="column.dataIndex === 'categoryName'">
                <a-select v-model:value="reqItemsDataSource[record.seq - 1].categoryName" style="width: 100%"
                    :options="categoryOptions" />
            </template>
            <template v-if="column.dataIndex === 'productName'">
                <a-select v-model:value="reqItemsDataSource[record.seq - 1].productName" style="width: 100%"
                    :options="productOptions" />
            </template>
            <template v-if="column.dataIndex === 'qtyBox'">
                <a-input v-model:value="reqItemsDataSource[record.seq - 1].qtyBox" />
            </template>
            <template v-if="column.dataIndex === 'qtyOrder'">
                <a-input v-model:value="reqItemsDataSource[record.seq - 1].qtyOrder" />
            </template>

            <template v-else-if="column.dataIndex === 'operation'">
                <a-popconfirm v-if="reqItemsDataSource.length" title="确定删除?" @confirm="onDelete(record.seq)">
                    <a>删除</a>
                </a-popconfirm>
            </template>
        </template>
    </a-table>

    <div>
        <a-upload action="https://www.mocky.io/v2/5cc8019d300000980a055e76" :multiple="true" @change="handleFileUpload"
            style="display: flex; justify-content: flex-end; margin-top: 10px; margin-right: 10px;">
            <a-button type="primary" preIcon="ant-design:plus-outlined">
                <upload-outlined></upload-outlined>
                附件上传
            </a-button>
        </a-upload>
        <span style="color: red;">说明： 请将规格明细（数量/份数）等信息以附件形式上传，以便开具酒单，附件大小不能超过10M。</span>
    </div>

    <div class="color-bar">
        审批节点设置
    </div>

    <div style="margin-top: 15px; margin-left: 0px;">
        <BasicForm @register="selectUserForm" />
    </div>
</template>

<script lang="ts" name="newbie-request-sample" setup>
import { useForm, BasicForm } from "/@/components/Form";
import { selectUserFormSchemas } from "./sample.data";
import { ref, computed, onMounted } from 'vue'
import type { Ref } from 'vue';
import { Nullable } from '@/views/newbie/types/basic-type'

const [selectUserForm, { getFieldsValue }] = useForm({
    schemas: selectUserFormSchemas,
    showActionButtonGroup: false,
});

function handleSubmit() {
    const values = getFieldsValue();
    console.log("----- submit values:", values);
    console.log("----- dataSource:", reqItemsDataSource.value);
}


// --------------- 动态INPUT 开始-----------------
interface IReqItem {
    seq: number;
    brandName: Nullable<string>;
    categoryName: Nullable<string>;
    productName: Nullable<string>;
    qtyBox: Nullable<number>;
    qtyOrder: Nullable<number>;
}

interface IFormData {
    title: Nullable<string>;
    requester: Nullable<string>;
    reqDept: Nullable<string>;
    reqDate: Nullable<string>;
    procNo: Nullable<string>;
    itemCategory: Nullable<number>;
    phone: Nullable<string>;
    fileUploadUrl: Nullable<string>;
    personInChargeId: Nullable<string>;
}

const columns = [
    {
        title: '序号',
        dataIndex: 'seq',
        width: '5%',
        align: 'center',
    },
    {
        title: '品牌',
        dataIndex: 'brandName',
        align: 'center',
        width: '20%',
    },
    {
        title: '类别',
        dataIndex: 'categoryName',
        align: 'center',
        width: '20%',
    },
    {
        title: '产品名称',
        dataIndex: 'productName',
        width: '30%',
        align: 'center',
    },
    {
        title: '箱数',
        dataIndex: 'qtyBox',
        width: '10%',
        align: 'center',
    },
    {
        title: '张数',
        dataIndex: 'qtyOrder',
        width: '10%',
        align: 'center',
    },
    {
        title: '操作',
        dataIndex: 'operation',
        width: '5%',
        align: 'center',
    },

];

// 整个表单的数据
const formData: Ref<IFormData> = ref({
    title: undefined,
    requester: undefined,
    reqDept: undefined,
    reqDate: undefined,
    procNo: undefined,
    itemCategory: undefined,
    phone: undefined,
    fileUploadUrl: undefined,
    personInChargeId: undefined,
})

const reqItemsDataSource: Ref<IReqItem[]> = ref([
    /*     {
            seq: 1,
            brandName: '青岛啤酒',
            categoryName: '瓶',
            productName: '青岛啤酒500ml',
            qtyBox: 10,
            qtyOrder: 2,
        }, */
]);
const count = computed(() => reqItemsDataSource.value.length + 1);


const itemCategoryOptions = ref([
    { label: '样品', value: 1 }
]);

// Brand options for dropdown
const brandOptions = ref([
    { label: '青岛啤酒', value: '1' },
    { label: '青岛纯生', value: '2' },
    { label: '雪花啤酒', value: '3' },
    { label: '百威啤酒', value: '4' },
]);
const categoryOptions = ref([
    { label: '瓶', value: '1' },
    { label: '罐', value: '2' },
    { label: '桶', value: '3' },
]);
const productOptions = ref([
    { label: '青岛啤酒500ml', value: '1' },
    { label: '青岛纯生500ml', value: '2' },
    { label: '雪花啤酒500ml', value: '3' },
    { label: '百威啤酒500ml', value: '4' },
    { label: '青岛啤酒经典易拉罐', value: '5' },
]);


const onDelete = (seq: number) => {
    reqItemsDataSource.value = reqItemsDataSource.value.filter(item => item.seq !== seq);
};
const handleAdd = () => {
    const newData: IReqItem = {
        seq: count.value,
        brandName: undefined,
        categoryName: undefined,
        productName: undefined,
        qtyBox: 1,
        qtyOrder: 1,
    };
    reqItemsDataSource.value.push(newData);
};

function handleFileUpload() {
    console.log("kkfk")
    // 提取URL, 保存到formData 
}

onMounted(() => {
    // 初始化时添加一行
    handleAdd();
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
