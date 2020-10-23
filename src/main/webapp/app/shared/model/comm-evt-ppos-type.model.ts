export interface ICommEvtPposType {
  id?: number;
  code?: string;
  description?: string;
}

export class CommEvtPposType implements ICommEvtPposType {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
