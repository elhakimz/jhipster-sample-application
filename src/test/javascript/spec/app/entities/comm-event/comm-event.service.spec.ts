import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommEventService } from 'app/entities/comm-event/comm-event.service';
import { ICommEvent, CommEvent } from 'app/shared/model/comm-event.model';

describe('Service Tests', () => {
  describe('CommEvent Service', () => {
    let injector: TestBed;
    let service: CommEventService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommEvent;
    let expectedResult: ICommEvent | ICommEvent[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommEventService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CommEvent(0, 'AAAAAAA', currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            started: currentDate.format(DATE_TIME_FORMAT),
            ended: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CommEvent', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            started: currentDate.format(DATE_TIME_FORMAT),
            ended: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            started: currentDate,
            ended: currentDate,
          },
          returnedFromService
        );

        service.create(new CommEvent()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommEvent', () => {
        const returnedFromService = Object.assign(
          {
            eventId: 'BBBBBB',
            started: currentDate.format(DATE_TIME_FORMAT),
            ended: currentDate.format(DATE_TIME_FORMAT),
            note: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            started: currentDate,
            ended: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CommEvent', () => {
        const returnedFromService = Object.assign(
          {
            eventId: 'BBBBBB',
            started: currentDate.format(DATE_TIME_FORMAT),
            ended: currentDate.format(DATE_TIME_FORMAT),
            note: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            started: currentDate,
            ended: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CommEvent', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
