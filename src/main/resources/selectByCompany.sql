select p.name, c.name
from person p join company c
on p.company_id = c.id
where c.id != 5;
/*
Вывод:
- имена всех person, которые не состоят в компании с id = 5
- название компании для каждого человека
*/

select c.name, count(p.name)
from company c join person p on c.id = p.company_id
group by c.name
order by count(p.name) desc
limit 1;
/*
- название компании с максимальным количеством человек + количество человек в этой компании
*/
