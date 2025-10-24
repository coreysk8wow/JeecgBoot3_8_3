<template>
    <div style="display: flex; align-items: center; justify-content: center; position: relative;">
        <h2 style="position: relative; z-index: 1; margin: 0; font-weight: bold;">样品领用申请单</h2>
        <a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleSubmit"
            style="position: absolute; right: 10px; z-index: 1;">发起流程</a-button>
    </div>

    <div class="color-bar">
        基本信息
    </div>

    <div class="color-bar">
        审批内容
    </div>

    <a-button class="editable-add-btn" style="margin-bottom: 8px;" @click="handleAdd">Add</a-button>
    <a-table bordered :data-source="dataSource" :columns="columns">
        <template #bodyCell="{ column, text, record }">
            <template v-if="column.dataIndex === 'brandName'">
                <div class="editable-cell">
                    <div v-if="editableData[record.key]" class="editable-cell-input-wrapper">
                        <a-select 
                            v-model:value="editableData[record.key].brandName" 
                            style="width: 100%"
                            :options="brandOptions"
                        />
                        <check-outlined class="editable-cell-icon-check" @click="save(record.key)" />
                    </div>
                    <div v-else class="editable-cell-text-wrapper">
                        {{ text || ' ' }}
                        <edit-outlined class="editable-cell-icon" @click="edit(record.key)" />
                    </div>
                </div>
            </template>
            <template v-else-if="column.dataIndex === 'operation'">
                <a-popconfirm v-if="dataSource.length" title="Sure to delete?" @confirm="onDelete(record.key)">
                    <a>Delete</a>
                </a-popconfirm>
            </template>
        </template>
    </a-table>

    <div class="color-bar">
        审批节点设置
    </div>

    <div style="margin-top: 15px; margin-left: 0px;">
        <BasicForm @register="selectUserForm" />
    </div>
</template>

<script lang="ts" name="newbie-applications-sample" setup>
import { useForm, BasicForm } from "/@/components/Form";
import { selectUserFormSchemas } from "./sample.data";
import { ref, reactive, computed } from 'vue'
import { CheckOutlined, EditOutlined } from '@ant-design/icons-vue';
import { cloneDeep } from 'lodash-es';
import type { Ref, UnwrapRef } from 'vue';

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
interface DataItem {
    key: string;
    name: string;
    age: number;
    address: string;
}

const columns = [
    {
        title: '序号',
        dataIndex: 'seq',
        width: '5%'
    },
    {
        title: '品牌',
        dataIndex: 'brandName',
    },
    {
        title: '类别',
        dataIndex: 'categoryName',
    },
    {
        title: '产品名称',
        dataIndex: 'productName',
        width: '30%'
    },
    {
        title: '箱数',
        dataIndex: 'qtyBox',
        width: '5%'
    },
    {
        title: '张数',
        dataIndex: 'qtyOrder',
        width: '5%'
    },
    {
        title: 'operation',
        dataIndex: 'operation',
        width: '5%'
    },

];
const dataSource: Ref<DataItem[]> = ref([
    {
        key: '0',
        name: 'Edward King 0',
        age: 32,
        address: 'London, Park Lane no. 0',
    },
    {
        key: '1',
        name: 'Edward King 1',
        age: 32,
        address: 'London, Park Lane no. 1',
    },
]);
const count = computed(() => dataSource.value.length + 1);
const editableData: UnwrapRef<Record<string, DataItem>> = reactive({});

// Brand options for dropdown
const brandOptions = ref([
    { label: '青岛啤酒', value: '青岛啤酒' },
    { label: '青岛纯生', value: '青岛纯生' },
    { label: '雪花啤酒', value: '雪花啤酒' },
    { label: '百威啤酒', value: '百威啤酒' },
]);

const edit = (key: string) => {
    editableData[key] = cloneDeep(dataSource.value.filter(item => key === item.key)[0]);
};
const save = (key: string) => {
    Object.assign(dataSource.value.filter(item => key === item.key)[0], editableData[key]);
    delete editableData[key];
};

const onDelete = (key: string) => {
    dataSource.value = dataSource.value.filter(item => item.key !== key);
};
const handleAdd = () => {
    const newData = {
        key: `${count.value}`,
        name: `Edward King ${count.value}`,
        age: 32,
        address: `London, Park Lane no. ${count.value}`,
    };
    dataSource.value.push(newData);
};




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


.editable-cell {
    position: relative;

    .editable-cell-input-wrapper,
    .editable-cell-text-wrapper {
        padding-right: 24px;
    }

    .editable-cell-text-wrapper {
        padding: 5px 24px 5px 5px;
    }

    .editable-cell-icon,
    .editable-cell-icon-check {
        position: absolute;
        right: 0;
        width: 20px;
        cursor: pointer;
    }

    .editable-cell-icon {
        margin-top: 4px;
        display: none;
    }

    .editable-cell-icon-check {
        line-height: 28px;
    }

    .editable-cell-icon:hover,
    .editable-cell-icon-check:hover {
        color: #108ee9;
    }

    .editable-add-btn {
        margin-bottom: 8px;
    }
}

.editable-cell:hover .editable-cell-icon {
    display: inline-block;
}
</style>
