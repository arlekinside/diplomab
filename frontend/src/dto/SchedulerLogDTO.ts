import {SchedulerTypeEnum} from "./SchedulerTypeEnum";

export default interface SchedulerLogDTO {
    id: number,
    type: SchedulerTypeEnum,
    processedNum: number,
    dateCreated: string,
    success: boolean
}