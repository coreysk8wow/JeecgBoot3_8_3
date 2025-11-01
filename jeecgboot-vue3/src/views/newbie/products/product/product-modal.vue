<template>
    <BasicModal @register="registerModal" :title="getTitle" @ok="handleSubmit" >
        <div class="space">
            <a-space direction="vertical">
                <a-space>
                    <span class="required-star">*</span>
                    产品： <a-input v-model:value="selectedProductName" placeholder="请输入产品名称" class="input-width" />
                </a-space>

                <a-space>
                    <span class="required-star">*</span>
                    品类：
                    <a-select v-model:value="selectedCategoryId" :options="categoryOptions" class="input-width" />
                </a-space>

                <a-space>
                    <span class="required-star">*</span>
                    品牌：
                    <a-select v-model:value="selectedBrandId" :options="brandOptions" class="input-width" />
                </a-space>
            </a-space>
        </div>
    </BasicModal>
</template>

<script lang="ts" setup>
import { BasicModal, useModalInner } from "/@/components/Modal";
import { computed, onMounted, Ref, ref, unref } from "vue";
import { addProduct, updateProductById, getAllBrands, getAllCategories } from "./product.api";
import { message } from 'ant-design-vue';
import { IProduct } from "/@/views/newbie/models/entities/product-intf";
import { Nullable } from '@/views/newbie/types/basic-type'

// 声明Emits
const emit = defineEmits(["success", "register"]);
const isUpdate = ref(true);

//表单赋值
const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    // 重置选中值
    selectedProductId = undefined;
    selectedProductName.value = undefined;
    selectedBrandId.value = undefined;
    selectedCategoryId.value = undefined;

    //重置表单
    setModalProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;
    if (unref(isUpdate)) {
        selectedProductId = data.record.productId;
        selectedProductName.value = data.record.productName;
        selectedBrandId.value = data.record.brandId;
        selectedCategoryId.value = data.record.categoryId;
    }
});

//设置标题
const getTitle = computed(() => (!unref(isUpdate) ? "新增品类" : "编辑品类"));

let selectedProductId: Nullable<string>;
const selectedProductName: Ref<Nullable<string>> = ref();
const selectedBrandId: Ref<Nullable<string>> = ref();
const selectedCategoryId: Ref<Nullable<string>> = ref();
const brandOptions = ref([]);
const categoryOptions = ref([]);

onMounted(async () => {
    const brands = await getAllBrands();
    brandOptions.value = brands.map((b) => ({ label: b.name, value: b.id }));

    const categories = await getAllCategories();
    categoryOptions.value = categories.map((c) => ({ label: c.name, value: c.id }));
});

/**
 * 点击提交按钮的value值
 * @param values
 */
async function handleSubmit() {
    try {
        setModalProps({ confirmLoading: true });
        
        // 验证：表单不能为空
        const isValid = validateForm();

        if (isUpdate.value) {
            console.log("--------更新产品 ID:", selectedProductId);

            const objUpdate: IProduct = {
                id: selectedProductId,
                name: selectedProductName.value,
                brandId: selectedBrandId.value,
                categoryId: selectedCategoryId.value,
            };
            await updateProductById(objUpdate);
        } else {
            await addProduct({
                id: undefined,
                name: selectedProductName.value,
                brandId: selectedBrandId.value,
                categoryId: selectedCategoryId.value,
            });
        }
        
        if (isValid) {
            //关闭弹窗
            closeModal();
            //刷新列表
            emit("success");
        }
        
    } finally {
        setModalProps({ confirmLoading: false });
    }
}

function validateForm(): true | false {
    // 在这里添加表单验证逻辑
    if (!selectedProductName.value) {
        message.error('产品名称不能为空');
        return false;
    }
    if (!selectedBrandId.value) {
        message.error('品牌不能为空');
        return false;
    }
    if (!selectedCategoryId.value) {
        message.error('品类不能为空');
        return false;
    }

    return true;
}

</script>

<style scoped>
.brand-form {
    margin-top: 50px;
    margin-left: 50px;
}

.space {
    margin-top: 50px;
    margin-left: 50px;
}

.required-star {
    color: red;
    margin-right: 5px;
}

.input-width {
    width: 300px;
}
</style>
