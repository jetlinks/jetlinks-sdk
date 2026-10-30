package org.jetlinks.sdk.server.auth.cmd;

import org.jetlinks.sdk.server.auth.DimensionUserBinding;
import org.jetlinks.sdk.server.auth.UserSaveRequest;
import org.jetlinks.sdk.server.utils.ObjectMappers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BasicAuthCommandsTest {
    @Test
    void userRequestRetainsPasswordAndAssociationsAcrossTransport() throws Exception {
        UserSaveRequest request = new UserSaveRequest();
        UserSaveRequest.User user = new UserSaveRequest.User();
        user.setId("user");
        user.setUsername("test-user");
        user.setPassword("test-only-password");
        user.setTelephone("123");
        user.setExtension("tenantId", "tenant");
        request.setUser(user);
        request.setExtension("source", "test-source");
        request.setRoleIdList(Set.of("role"));
        request.setBusinessApplicationIdList(Set.of("application"));
        CreateUserCommand original = new CreateUserCommand().setRequest(request);
        Map<String, Object> wire = ObjectMappers.JSON_MAPPER.readValue(
            ObjectMappers.JSON_MAPPER.writeValueAsBytes(original.readable()), Map.class);
        CreateUserCommand decoded = new CreateUserCommand().with(wire);
        assertEquals("CreateUser", decoded.getCommandId());
        assertEquals("test-source", decoded.getRequest().getExtension("source"));
        assertEquals("test-only-password", decoded.getRequest().getUser().getPassword());
        assertEquals("tenant", decoded.getRequest().getUser().getExtension("tenantId"));
        assertEquals(Set.of("role"), decoded.getRequest().getRoleIdList());
        assertEquals(Set.of("application"), decoded.getRequest().getBusinessApplicationIdList());
        UpdateUserCommand update = new UpdateUserCommand().setId("target").setRequest(decoded.getRequest());
        assertEquals("UpdateUser", update.getCommandId());
        assertEquals("target", update.getId());
        assertEquals("123", update.getRequest().getUser().getTelephone());
    }

    @Test
    void memberBindingRetainsRelationsAndFeatureNamesAcrossTransport() throws Exception {
        DimensionUserBinding binding = new DimensionUserBinding();
        binding.setUserId("user");
        binding.setDimensionTypeId("organization");
        binding.setDimensionId("org");
        binding.setRelation("manager");
        binding.setRelationName("Manager");
        binding.setFeatures(new String[]{"mergeChildrenPermission"});
        BindDimensionUsersCommand original = new BindDimensionUsersCommand().setBindings(List.of(binding));
        Map<String, Object> wire = ObjectMappers.JSON_MAPPER.readValue(
            ObjectMappers.JSON_MAPPER.writeValueAsBytes(original.readable()), Map.class);
        BindDimensionUsersCommand decoded = new BindDimensionUsersCommand().with(wire);
        assertEquals("BindDimensionUsers", decoded.getCommandId());
        DimensionUserBinding actual = decoded.getBindings().get(0);
        assertEquals("user", actual.getUserId());
        assertEquals("organization", actual.getDimensionTypeId());
        assertEquals("Manager", actual.getRelationName());
        assertEquals("org", actual.getDimensionId());
        assertEquals("manager", actual.getRelation());
        assertArrayEquals(binding.getFeatures(), actual.getFeatures());
        UnbindDimensionUsersCommand unbind = new UnbindDimensionUsersCommand()
            .setType("organization")
            .setId("org")
            .setUsers(List.of("user"));
        assertEquals("UnbindDimensionUsers", unbind.getCommandId());
        assertEquals(List.of("user"), unbind.getUsers());
        assertEquals("org", unbind.getId());
        assertEquals("organization", unbind.getType());
    }
}
