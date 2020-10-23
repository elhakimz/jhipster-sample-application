import { ICommEvent } from 'app/shared/model/comm-event.model';
import { ICommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';

export interface ICommEventPurpose {
  id?: number;
  description?: string;
  communicationEvent?: ICommEvent;
  purposeType?: ICommEvtPposType;
}

export class CommEventPurpose implements ICommEventPurpose {
  constructor(
    public id?: number,
    public description?: string,
    public communicationEvent?: ICommEvent,
    public purposeType?: ICommEvtPposType
  ) {}
}
