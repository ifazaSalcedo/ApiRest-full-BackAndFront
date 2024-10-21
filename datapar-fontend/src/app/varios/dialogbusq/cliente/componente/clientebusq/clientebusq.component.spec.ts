import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientebusqComponent } from './clientebusq.component';

describe('ClientebusqComponent', () => {
  let component: ClientebusqComponent;
  let fixture: ComponentFixture<ClientebusqComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientebusqComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientebusqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
