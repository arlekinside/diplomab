import MoneyDTO from "./MoneyDTO";
import {RecurringCycleEnum} from "./RecurringCycleEnum";

export default interface MoneyFlowDTO {
    id?: number
    money: MoneyDTO
    cycle: RecurringCycleEnum
    dateCreated?: string
}