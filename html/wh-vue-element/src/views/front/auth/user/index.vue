<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="params">
        <el-form-item>
          <el-input v-model="params.username" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" :loading="loading" type="info" v-on:click="search" circle></el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <template>
      <el-table :data="list" highlight-current-row :loading="loading" style="width: 100%;">
        <el-table-column type="id" width="60">
        </el-table-column>
        <el-table-column prop="username" label="姓名" width="120">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="100" :formatter="formatSex">
        </el-table-column>
        <el-table-column prop="city" label="城市" width="100">
        </el-table-column>
        <el-table-column prop="sign" label="签名" width="120">
        </el-table-column>
        <el-table-column prop="classify" label="职业" min-width="180">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-row>
              <el-button @click="show(scope.row)" size="mini" type="primary" icon="el-icon-view" circle></el-button>
              <el-button @click="edit(scope.row)" size="mini" type="primary" icon="el-icon-edit" circle></el-button>
            </el-row>
          </template>
        </el-table-column>
      </el-table>
    </template>
  </section>
</template>

<script src="@/services/front/auth/user/userController.js"/>
