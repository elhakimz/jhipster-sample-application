import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { ICommEvent } from 'app/shared/model/comm-event.model';

export interface ICommWorkEffort {
  id?: number;
  description?: string;
  workEffort?: IWorkEffort;
  communicationEvent?: ICommEvent;
}

export class CommWorkEffort implements ICommWorkEffort {
  constructor(public id?: number, public description?: string, public workEffort?: IWorkEffort, public communicationEvent?: ICommEvent) {}
}
