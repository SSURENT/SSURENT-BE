package ssurent.ssurentbe.domain.users.dto;

public record SignupRequest(
        String studentNum,
        String name,
        String phoneNum,
        String password
) {
    @Override
    public String toString(){
        return "SignupRequest[studentNum=" + studentNum
                + ", name=" + name
                + ", phoneNum=" + phoneNum
                + ", password=***]";
    }
}
