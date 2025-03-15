import { Shift } from "./shift"

export interface WorkingDay {
    id?: string
    dayOfWeek: string
    isOpen: boolean
    shiftList: Shift[]
}
