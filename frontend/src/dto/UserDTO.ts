import {UserRoleEnum} from "./UserRoleEnum";

export default interface UserDTO {
    username: string,
    role: UserRoleEnum
}