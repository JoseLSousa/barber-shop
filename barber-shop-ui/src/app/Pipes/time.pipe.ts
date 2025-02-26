import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'time'
})
export class TimePipe implements PipeTransform {

  transform(value: string): string {

    const [hour, minute] = value.split(':')
    return hour + ':' + minute;
  }

}
