import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InicioHome1Component } from './inicio-home1.component';

describe('InicioHome1Component', () => {
  let component: InicioHome1Component;
  let fixture: ComponentFixture<InicioHome1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InicioHome1Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InicioHome1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
