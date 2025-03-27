package org.jetlinks.sdk.server.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.hswebframework.web.authorization.Authentication;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.*;

@Getter
@Setter
public class GrantScope implements Externalizable {

    @Schema(title = "授权维度", description = "如授权访问指定的组织,角色等.")
    private List<DimensionInfo> dimensions;

    @Schema(title = "授权权限", description = "如授权访问指定的权限等.")
    private List<Permit> permissions;

    // 预留,权限分组
    @Schema(title = "分组", hidden = true)
    private List<String> groups;

    /**
     * @see Authentication#getAttributes()
     */
    private Map<String, Object> attributes;

    public GrantScope() {
    }

    @JsonIgnore
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(dimensions)
            && CollectionUtils.isEmpty(permissions)
            && CollectionUtils.isEmpty(groups);
    }

    public GrantScope addDimension(String type, String id) {
        return addDimension(type, id, null);
    }

    public GrantScope addDimension(String type, String id, Map<String, Object> options) {
        if (dimensions == null) {
            dimensions = new ArrayList<>();
        }
        dimensions.add(new DimensionInfo(type, id, options));
        return this;
    }


    public GrantScope addPermission(String id, String... action) {
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        permissions.add(Permit.of(id, action));
        return this;
    }

    public GrantScope addAttribute(String key, String value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(key,value);
        return this;
    }

    @Getter
    @Setter
    public static class Permit implements Externalizable {

        @Schema(title = "权限ID")
        private String id;

        @Schema(title = "权限动作")
        private Set<String> actions;

        public Permit() {
        }

        // permit:action1,action2
        public Permit(String idAndActions) {
            String[] split = idAndActions.split(":");
            this.id = split[0];
            this.actions = Sets.newHashSet(split[1].split(","));
        }

        public static Permit of(String id, Set<String> actions) {
            Permit permit = new Permit();
            permit.setId(id);
            permit.setActions(actions);
            return permit;
        }

        public static Permit of(String id, String... actions) {
            return of(id, Sets.newHashSet(actions));
        }


        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            SerializeUtils.writeNullableUTF(id, out);
            SerializeUtils.writeObject(actions, out);
        }

        @Override
        @SuppressWarnings("unchecked")
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            id = SerializeUtils.readNullableUTF(in);
            actions = (Set<String>) SerializeUtils.readObject(in);
        }
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

        SerializeUtils.writeObject(groups, out);

        int size = dimensions == null ? 0 : dimensions.size();
        out.writeInt(size);
        if (dimensions != null) {
            for (DimensionInfo dimension : dimensions) {
                dimension.writeExternal(out);
            }
        }

        size = permissions == null ? 0 : permissions.size();
        out.writeInt(size);
        if (permissions != null) {
            for (Permit permission : permissions) {
                permission.writeExternal(out);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        groups = (List<String>) SerializeUtils.readObject(in);

        int size = in.readInt();
        if (size > 0) {
            dimensions = new java.util.ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                DimensionInfo dimension = new DimensionInfo();
                dimension.readExternal(in);
                dimensions.add(dimension);
            }
        }

        size = in.readInt();
        if (size > 0) {
            permissions = new java.util.ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                Permit permission = new Permit();
                permission.readExternal(in);
                permissions.add(permission);
            }
        }
    }
}
