import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardRentalComponent } from './dashboard-rental.component';

describe('DashboardRentalComponent', () => {
  let component: DashboardRentalComponent;
  let fixture: ComponentFixture<DashboardRentalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardRentalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardRentalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
