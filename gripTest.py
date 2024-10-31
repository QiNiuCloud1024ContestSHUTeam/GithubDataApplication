import networkx as nx
import matplotlib.pyplot as plt
G = nx.DiGraph()
edges = [
    ("A","B"),("A","C"),("A","D"),
    ("B","A"),("B","D"),
    ("C","A"),
    ("D","B"),("D","C")
]

for edge in edges:
    G.add_edge(edge[0],edge[1])

nx.draw_networkx(G)
plt.show()