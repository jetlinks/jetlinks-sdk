package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
public class MenuInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    @Schema(description = "菜单所属应用ID")
    private String appId;

    @Schema(description = "菜单所有者")
    private String owner;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "所属应用")
    private String application;

    @Schema(description = "描述")
    private String describe;

    @Schema(description = "URL,路由")
    private String url;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "状态,0为禁用,1为启用")
    private Byte status;

    @Schema(description = "父节点ID")
    private String parentId;

    @Schema(description = "树结构路径")
    private String path;

    @Schema(description = "排序序号")
    private Long sortIndex;

    @Schema(description = "树层级")
    private Integer level;

    @Schema(description = "绑定权限信息")
    private List<PermissionInfo> permissions;

    @Schema(description = "数据权限控制")
    private AccessSupportState accessSupport;

    @Schema(description = "关联菜单,accessSupport为indirect时不能为空")
    private List<String> indirectMenus;

    @Schema(description = "关联资产类型,accessSupport为support有值")
    private String assetType;

    @Schema(description = "按钮定义信息")
    private List<MenuButtonInfo> buttons;

    @Schema(description = "其他配置信息")
    private Map<String, Object> options;


    @Schema(title = "国际化信息定义")
    private Map<String, Map<String, String>> i18nMessages;


    @Getter
    @Setter
    public static class PermissionInfo implements Serializable {
        private static final long serialVersionUID = 1L;

        @Schema(description = "权限ID")
        private String permission;

        @Schema(description = "权限名称")
        private String name;

        @Schema(description = "权限操作")
        private Set<String> actions;

        public static PermissionInfo of(String permission, Set<String> actions) {
            PermissionInfo permissionInfo = new PermissionInfo();
            permissionInfo.setPermission(permission);
            permissionInfo.setActions(actions);
            return permissionInfo;
        }

    }

    @Getter
    public enum AccessSupportState {
        //支持
        support,
        //不支持
        unsupported,
        //间接的
        indirect;
    }

    @Getter
    @Setter
    public static class MenuButtonInfo implements Serializable {
        private static final long serialVersionUID = 1L;

        @Schema(description = "按钮ID")
        private String id;

        @Schema(description = "按钮名称")
        private String name;

        @Schema(description = "说明")
        private String description;

        @Schema(description = "权限信息")
        private List<PermissionInfo> permissions;

        @Schema(description = "其他配置")
        private Map<String, Object> options;

        @Schema(description = "i18n配置")
        private Map<String, String> i18nMessages;

        public static MenuButtonInfo of(String id, String name, String permission, String... actions) {
            MenuButtonInfo info = new MenuButtonInfo();
            info.setId(id);
            info.setName(name);
            info.setPermissions(Arrays.asList(PermissionInfo.of(permission, new HashSet<>(Arrays.asList(actions)))));
            return info;
        }
    }


}
