import { Moment } from 'moment';
import { CasusStatusType } from 'app/shared/model/enumerations/casus-status-type.model';

export interface ICasus {
  id?: number;
  code?: string;
  description?: string;
  start?: Moment;
  statusType?: CasusStatusType;
}

export class Casus implements ICasus {
  constructor(
    public id?: number,
    public code?: string,
    public description?: string,
    public start?: Moment,
    public statusType?: CasusStatusType
  ) {}
}
