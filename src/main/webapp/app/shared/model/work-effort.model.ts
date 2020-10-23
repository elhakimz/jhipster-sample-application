import { Moment } from 'moment';
import { WorkEffortType } from 'app/shared/model/enumerations/work-effort-type.model';

export interface IWorkEffort {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  scheduledStart?: Moment;
  scheduledCompletion?: Moment;
  totalMoneyAllowed?: number;
  totalHoursAllowed?: number;
  estimatedHours?: number;
  workEffortType?: WorkEffortType;
}

export class WorkEffort implements IWorkEffort {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public scheduledStart?: Moment,
    public scheduledCompletion?: Moment,
    public totalMoneyAllowed?: number,
    public totalHoursAllowed?: number,
    public estimatedHours?: number,
    public workEffortType?: WorkEffortType
  ) {}
}
