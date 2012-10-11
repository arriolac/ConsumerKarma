from django.http import HttpResponse

import ParsePy

# Initialize Parse
ParsePy.APPLICATION_ID = "xXnJr64tDsIdqugTpfJxg9nKaUE6oEH6tg3M7j74"
ParsePy.REST_API_KEY = "dAc70AZmfJNuvIDExvBaZ5QuI6XOEk3IkpUxYsja"


def index(request):
    return HttpResponse('Hello you got here safely!')

def produce(request):
    # create random item
    newItem = ParsePy.ParseObject("Item")
    newItem.title="Test Item"
    newItem.description="Testing parse with Django"
    newItem.unethical_animal_practices=False
    newItem.corporate_irresponsibility=False
    newItem.environmentally_unsustainable=False
    newItem.exploited_labor=False
    newItem.save()
