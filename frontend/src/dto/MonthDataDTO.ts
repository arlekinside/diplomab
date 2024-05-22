import MoneyDTO from "./MoneyDTO";

export default interface MonthDataDTO {
    incomes: MoneyDTO,
    expenses: MoneyDTO,
    savings: MoneyDTO,
    hh: MoneyDTO,
}