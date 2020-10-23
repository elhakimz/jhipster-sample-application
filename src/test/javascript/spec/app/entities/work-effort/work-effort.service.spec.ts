import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { WorkEffortService } from 'app/entities/work-effort/work-effort.service';
import { IWorkEffort, WorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortType } from 'app/shared/model/enumerations/work-effort-type.model';

describe('Service Tests', () => {
  describe('WorkEffort Service', () => {
    let injector: TestBed;
    let service: WorkEffortService;
    let httpMock: HttpTestingController;
    let elemDefault: IWorkEffort;
    let expectedResult: IWorkEffort | IWorkEffort[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WorkEffortService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new WorkEffort(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 0, 0, 0, WorkEffortType.PROGRAM);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            scheduledStart: currentDate.format(DATE_FORMAT),
            scheduledCompletion: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a WorkEffort', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            scheduledStart: currentDate.format(DATE_FORMAT),
            scheduledCompletion: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            scheduledStart: currentDate,
            scheduledCompletion: currentDate,
          },
          returnedFromService
        );

        service.create(new WorkEffort()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WorkEffort', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            scheduledStart: currentDate.format(DATE_FORMAT),
            scheduledCompletion: currentDate.format(DATE_FORMAT),
            totalMoneyAllowed: 1,
            totalHoursAllowed: 1,
            estimatedHours: 1,
            workEffortType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            scheduledStart: currentDate,
            scheduledCompletion: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of WorkEffort', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            scheduledStart: currentDate.format(DATE_FORMAT),
            scheduledCompletion: currentDate.format(DATE_FORMAT),
            totalMoneyAllowed: 1,
            totalHoursAllowed: 1,
            estimatedHours: 1,
            workEffortType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            scheduledStart: currentDate,
            scheduledCompletion: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a WorkEffort', () => {
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
