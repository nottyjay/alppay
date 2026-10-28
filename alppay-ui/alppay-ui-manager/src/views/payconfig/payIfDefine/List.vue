<template>
  <page-header-wrapper>
    <div style="background-color: #f0f2f5; padding: 20px; border-radius: 10px">
      <AlppayCard ref="infoCard" :reqCardListFunc="reqCardListFunc" :span="vdata.alppayCard.span"
        :height="vdata.alppayCard.height" :name="vdata.alppayCard.name" :addAuthority="vdata.alppayCard.addAuthority"
        @addalppayCard="addOrEdit">
        <template #cardContentSlot="{ record }">
          <div :style="{ height: vdata.alppayCard.height + 'px' }" class="alppay-card-content">
            <!-- 卡片自定义样式 -->
            <div class="alppay-card-content-header" :style="{
              backgroundColor: record.bgColor,
              height: vdata.alppayCard.height / 2 + 'px',
            }">
              <img v-if="record.icon" :src="record.icon" :style="{ height: vdata.alppayCard.height / 5 + 'px' }" />
            </div>
            <div class="alppay-card-content-body" :style="{ height: vdata.alppayCard.height / 2 - 50 + 'px' }">
              <div class="title">
                {{ record.ifName }}
              </div>
            </div>
            <!-- 卡片底部操作栏 -->
            <div class="alppay-card-ops">
              <a-tooltip placement="top" title="编辑">
                <EditOutlined key="edit" type="edit" @click="addOrEdit(record.ifCode)" />
              </a-tooltip>
              <a-tooltip placement="top" title="删除">
                <DeleteOutlined key="delete" type="delete" @click="del(record.ifCode)" />
              </a-tooltip>
            </div>
          </div>
        </template>
      </AlppayCard>
    </div>
    <!-- 新增页面组件  -->
    <PayIfDefineAddOrEdit ref="payIfDefineAddOrEdit" :callbackFunc="refCardList" />
  </page-header-wrapper>
</template>

<script setup lang="ts">
import { API_URL_IFDEFINES_LIST, req } from '@/api/manage'
import PayIfDefineAddOrEdit from './AddOrEdit.vue'
import { getCurrentInstance, reactive, ref } from 'vue'

const { $infoBox, $access } = getCurrentInstance()!.appContext.config.globalProperties

const vdata = reactive({
  alppayCard: {
    name: '支付接口',
    height: 200,
    span: { xxl: 8, xl: 4, lg: 4, md: 3, sm: 2, xs: 1 },
    addAuthority: $access('ENT_PC_IF_DEFINE_ADD'),
  },
})

const infoCard = ref()
const payIfDefineAddOrEdit = ref()

// 请求支付接口定义数据
function reqCardListFunc() {
  return req.list(API_URL_IFDEFINES_LIST)
}

// 刷新card列表
function refCardList() {
  infoCard.value.refCardList()
}

function addOrEdit(ifCode) {
  payIfDefineAddOrEdit.value.show(ifCode)
}

function del(ifCode) {
  $infoBox.confirmDanger('确认删除？', '', () => {
    req.delById(API_URL_IFDEFINES_LIST, ifCode).then((res) => {
      $infoBox.message.success('删除成功！')
      refCardList()
    })
  })
}
</script>

<style lang="less" scoped>
.alppay-card-content {
  width: 100%;
  position: relative;
  background-color: @jee-card-back;
  border-radius: 6px;
  overflow: hidden;
}

.alppay-card-ops {
  width: 100%;
  height: 50px;
  background-color: @jee-card-back;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  border-top: 1px solid @jee-back;
  position: absolute;
  bottom: 0;
}

.alppay-card-content-header {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

.alppay-card-content-body {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

.title {
  font-size: 13px;
  font-family: PingFang SC,
    PingFang SC-Bold;
  font-weight: 700;
  color: #1a1a1a;
  letter-spacing: 1px;
}
</style>
