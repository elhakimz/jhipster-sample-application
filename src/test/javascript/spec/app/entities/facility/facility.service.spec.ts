import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IFacility, Facility } from 'app/shared/model/facility.model';
import { FacilityType } from 'app/shared/model/enumerations/facility-type.model';

describe('Service Tests', () => {
  describe('Facility Service', () => {
    let injector: TestBed;
    let service: FacilityService;
    let httpMock: HttpTestingController;
    let elemDefault: IFacility;
    let expectedResult: IFacility | IFacility[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FacilityService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Facility(0, 'AAAAAAA', 'AAAAAAA', 0, FacilityType.WAREHOUSE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Facility', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Facility()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Facility', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            description: 'BBBBBB',
            area: 1,
            facilityType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Facility', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            description: 'BBBBBB',
            area: 1,
            facilityType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Facility', () => {
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
