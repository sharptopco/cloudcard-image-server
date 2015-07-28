<%@ page import="com.cloudcardtools.bbts.Role; com.cloudcardtools.bbts.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="user.username.label" default="Username"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="username" required="" value="${userInstance?.username}"/>

</div>

%{--<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">--}%
%{--<label for="password">--}%
%{--<g:message code="user.password.label" default="Password" />--}%
%{--<span class="required-indicator">*</span>--}%
%{--</label>--}%
%{--<g:textField name="password" required="" value="${userInstance?.password}"/>--}%

%{--</div>--}%

<!-- CHANGE PASSWORD -->
<div id="changePassword-div" class="fieldcontain">
    <label for="changePassword"><g:message code="user.changePassword.label" default="Change Password"/></label>
    <g:checkBox name="changePassword" id="changePassword"/>
</div>

<!-- PASSWORD -->
<div id="password-div" class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} "
     style="display: none;">
    <label for="password"><g:message code="user.newPassword.label" default="New Password"/></label>
    <g:passwordField name="newPassword" id="newPassword"/>
    <g:hiddenField name="password" value="${userInstance?.password}"/>
</div>

<!-- CONFIRM PASSWORD -->
<div id="confirmPassword-div" class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required"
     style="display: none;">
    <label for="confirmPassword"><g:message code="user.confirmPassword.label" default="Confirm Password"/></label>
    <g:passwordField name="confirmPassword" id="confirmPassword"/>
</div>

<script>
    $(document).ready(function () {
        $("#changePassword").change(function () {
            if ($("#changePassword").is(':checked')) {
                $("#password-div").show();
                $("#confirmPassword-div").show();
            } else {
                $("#password").val("");
                $("#confirmPassword").val("");
                $("#password-div").hide();
                $("#confirmPassword-div").hide();
            }
        });
    });

    if (${ !userInstance?.id }) {
        $("#password-div").show();
        $("#confirmPassword-div").show();
        $("#changePassword-div").hide();
    }
</script>

<hr style="width: 15%; margin-left: 17.5%; margin-top: 1em;"/>

<!-- ROLES -->
<g:each var="role" in="${com.cloudcardtools.bbts.Role.list()}" status="i">
    <div class="fieldcontain">
        <label for="${role?.authority}">${!i ? 'Roles' : ''}</label>
        <g:checkBox name="${role?.authority}" id="${role?.authority}"
                    checked="${userInstance?.hasRole(role.authority)}"/>
        ${role?.authority?.substring(5)?.toLowerCase()?.capitalize()}
    </div>
</g:each>

<hr style="width: 15%; margin-left: 17.5%; margin-top: 1em;"/>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
    <label for="accountExpired">
        <g:message code="user.accountExpired.label" default="Account Expired"/>

    </label>
    <g:checkBox name="accountExpired" value="${userInstance?.accountExpired}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
    <label for="accountLocked">
        <g:message code="user.accountLocked.label" default="Account Locked"/>

    </label>
    <g:checkBox name="accountLocked" value="${userInstance?.accountLocked}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
    <label for="enabled">
        <g:message code="user.enabled.label" default="Enabled"/>

    </label>
    <g:checkBox name="enabled" value="${userInstance?.enabled}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
    <label for="passwordExpired">
        <g:message code="user.passwordExpired.label" default="Password Expired"/>

    </label>
    <g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}"/>

</div>

