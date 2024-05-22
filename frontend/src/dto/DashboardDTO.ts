import MoneyDTO from "./MoneyDTO";
import MonthDataDTO from "./MonthDataDTO";

export default interface DashboardDTO {
    budget: MoneyDTO,
    monthIncome: MoneyDTO,
    monthExpense: MoneyDTO,
    hhRate: MoneyDTO,
    savingsTotal: MoneyDTO,
    savingsLeft: MoneyDTO

    lastMonth: MonthDataDTO,
    currentMonth: MonthDataDTO,
    nextMonth: MonthDataDTO,
}