import MoneyDTO from "./MoneyDTO";
import {RecurringCycleEnum} from "./RecurringCycleEnum";

export default interface SavingDTO {
    id?: number
    name: string
    money?: MoneyDTO
    target: MoneyDTO
    monthlyPercent: number
    finished?: boolean
    dateCreated?: string
}