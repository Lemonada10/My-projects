import json
import sys
import random
import math

def load_json(filename):
    with open(filename, 'r') as file:
        return json.load(file)

def display_menu():
    print("1. Display Global Statistics")
    print("2. Display Base Station Statistics")
    print("3. Check Coverage")
    print("4. Exit")

def calculate_global_statistics(data):
    base_stations = data['baseStations']
    total_base_stations = len(base_stations)
    total_antennas = sum(len(bs['ants']) for bs in base_stations)
    antennas_per_base_station = [len(bs['ants']) for bs in base_stations]
    max_antennas = max(antennas_per_base_station)
    min_antennas = min(antennas_per_base_station)
    avg_antennas = total_antennas / total_base_stations
    
    # Calculate coverage details
    lat_range = data['max_lat'] - data['min_lat']
    lon_range = data['max_lon'] - data['min_lon']
    step = data['step']
    total_points = int((lat_range / step + 1) * (lon_range / step + 1))
    
    point_coverage = {}
    for bs in base_stations:
        for ant in bs['ants']:
            for point in ant['pts']:
                pt = tuple(point[:2])
                if pt not in point_coverage:
                    point_coverage[pt] = []
                point_coverage[pt].append(ant['id'])
    
    covered_by_one = sum(1 for pts in point_coverage.values() if len(pts) == 1)
    covered_by_more = sum(1 for pts in point_coverage.values() if len(pts) > 1)
    not_covered = total_points - (covered_by_one + covered_by_more)
    max_antennas_covering_one_point = max(len(pts) for pts in point_coverage.values())
    avg_antennas_per_point = sum(len(pts) for pts in point_coverage.values()) / len(point_coverage)
    percentage_covered = (covered_by_one + covered_by_more) / total_points * 100
    
    max_coverage_antenna = max(point_coverage, key=lambda k: len(point_coverage[k]))
    max_coverage_details = next((bs, ant) for bs in base_stations for ant in bs['ants'] if ant['id'] == max(point_coverage[max_coverage_antenna]))

    print(f"Total number of base stations: {total_base_stations}")
    print(f"Total number of antennas: {total_antennas}")
    print(f"Max, min and average number of antennas per base station: {max_antennas}, {min_antennas}, {avg_antennas:.2f}")
    print(f"Total number of points covered by exactly one antenna: {covered_by_one}")
    print(f"Total number of points covered by more than one antenna: {covered_by_more}")
    print(f"Total number of points not covered by any antenna: {not_covered}")
    print(f"Maximum number of antennas covering one point: {max_antennas_covering_one_point}")
    print(f"Average number of antennas covering a point: {avg_antennas_per_point:.2f}")
    print(f"Percentage of covered area: {percentage_covered:.2f}%")
    print(f"Base station and antenna covering the maximum number of points: Base station {max_coverage_details[0]['id']}, Antenna {max_coverage_details[1]['id']}")

def calculate_base_station_statistics(data, station_id):
    base_station = next(bs for bs in data['baseStations'] if bs['id'] == station_id)
    total_antennas = len(base_station['ants'])
    
    point_coverage = {}
    for ant in base_station['ants']:
        for point in ant['pts']:
            pt = tuple(point[:2])
            if pt not in point_coverage:
                point_coverage[pt] = []
            point_coverage[pt].append(ant['id'])
    
    lat_range = data['max_lat'] - data['min_lat']
    lon_range = data['max_lon'] - data['min_lon']
    step = data['step']
    total_points = int((lat_range / step + 1) * (lon_range / step + 1))
    
    covered_by_one = sum(1 for pts in point_coverage.values() if len(pts) == 1)
    covered_by_more = sum(1 for pts in point_coverage.values() if len(pts) > 1)
    not_covered = total_points - (covered_by_one + covered_by_more)
    max_antennas_covering_one_point = max(len(pts) for pts in point_coverage.values())
    avg_antennas_per_point = sum(len(pts) for pts in point_coverage.values()) / len(point_coverage)
    percentage_covered = (covered_by_one + covered_by_more) / total_points * 100
    
    max_coverage_antenna = max(point_coverage, key=lambda k: len(point_coverage[k]))
    max_coverage_details = next(ant for ant in base_station['ants'] if ant['id'] == max(point_coverage[max_coverage_antenna]))

    print(f"Base Station ID: {station_id}")
    print(f"Total number of antennas: {total_antennas}")
    print(f"Total number of points covered by exactly one antenna: {covered_by_one}")
    print(f"Total number of points covered by more than one antenna: {covered_by_more}")
    print(f"Total number of points not covered by any antenna: {not_covered}")
    print(f"Maximum number of antennas covering one point: {max_antennas_covering_one_point}")
    print(f"Average number of antennas covering a point: {avg_antennas_per_point:.2f}")
    print(f"Percentage of covered area: {percentage_covered:.2f}%")
    print(f"Antenna covering the maximum number of points: Antenna {max_coverage_details['id']}")

def find_nearest_antenna(data, lat, lon):
    min_distance = float('inf')
    nearest_antenna = None
    nearest_base_station = None
    
    for bs in data['baseStations']:
        for ant in bs['ants']:
            for point in ant['pts']:
                distance = math.sqrt((lat - point[0]) ** 2 + (lon - point[1]) ** 2)
                if distance < min_distance:
                    min_distance = distance
                    nearest_antenna = ant
                    nearest_base_station = bs
    
    return nearest_base_station, nearest_antenna

def check_coverage(data, lat, lon):
    point_coverage = {}
    for bs in data['baseStations']:
        for ant in bs['ants']:
            for point in ant['pts']:
                pt = tuple(point[:2])
                if pt not in point_coverage:
                    point_coverage[pt] = []
                point_coverage[pt].append((bs['id'], ant['id'], point[2]))
    
    if (lat, lon) in point_coverage:
        print(f"Point ({lat}, {lon}) is covered by the following antennas:")
        for coverage in point_coverage[(lat, lon)]:
            print(f"Base Station ID: {coverage[0]}, Antenna ID: {coverage[1]}, Received Power: {coverage[2]}")
    else:
        nearest_base_station, nearest_antenna = find_nearest_antenna(data, lat, lon)
        print(f"Point ({lat}, {lon}) is not explicitly covered. Nearest coverage:")
        print(f"Base Station ID: {nearest_base_station['id']}, Antenna ID: {nearest_antenna['id']}, Location: ({nearest_antenna['pts'][0][0]}, {nearest_antenna['pts'][0][1]})")

def main():
    if len(sys.argv) != 2:
        print("Usage: python3 assignment2.py <test_file.json>")
        return
    
    filename = sys.argv[1]
    data = load_json(filename)
    
    while True:
        display_menu()
        choice = input("Enter your choice: ")
        
        if choice == '1':
            calculate_global_statistics(data)
        elif choice == '2':
            sub_choice = input("1. Random Station\n2. Choose by ID\nEnter your choice: ")
            if sub_choice == '1':
                station_id = random.choice(data['baseStations'])['id']
                calculate_base_station_statistics(data, station_id)
            elif sub_choice == '2':
                station_id = int(input("Enter Base Station ID: "))
                calculate_base_station_statistics(data, station_id)
        elif choice == '3':
            lat = float(input("Enter latitude: "))
            lon = float(input("Enter longitude: "))
            check_coverage(data, lat, lon)
        elif choice == '4':
            print("See you again!")
            break
        else:
            print("Invalid choice, please try again.")

if __name__ == "__main__":
    main()
